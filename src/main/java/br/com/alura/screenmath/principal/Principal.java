package br.com.alura.screenmath.principal;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmath.model.DadosEpisodio;
import br.com.alura.screenmath.model.DadosSerie;
import br.com.alura.screenmath.model.DadosTemporada;
import br.com.alura.screenmath.model.Episodio;
import br.com.alura.screenmath.model.Genero;

import br.com.alura.screenmath.model.Serie;
import br.com.alura.screenmath.repository.SerieRepository;
import br.com.alura.screenmath.service.ConsumoAPI;
import br.com.alura.screenmath.service.ConverteDados;

public class Principal {
    
    public final String ENDERECO = "https://www.omdbapi.com/?t=";
    public final String API_KEY = "&apikey=6585022c";

    private DateTimeFormatter formatador =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumirAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private List<Serie> listaDeSeries = new ArrayList<>();
    
    private SerieRepository repository;
    
    public Principal(SerieRepository repository) {
        this.repository = repository;
        this.listaDeSeries = repository.findAll();
    }

    public void menuPrincipal(){
        int voto = -1;
        do{
        System.out.println("----- MENU SÉRIES -----");
        System.out.println("[1] - Buscar Séries");
        System.out.println("[2] - Buscar Episódios");
        System.out.println("[3] - Lista de Buscas");
        System.out.println("[4] - Buscar Série por titulo");
        System.out.println("[5] - Top5 melhores série");
        System.out.println("[6] - Buscar serie por Ator");
        System.out.println("[7] - Buscar por Categoria");
        System.out.println("[8] - Buscar Episodio por trecho");
        System.out.println("[9] - Top 5 Episodios");
        System.out.println("[0] - Sair");
        
        voto = teclado.nextInt();
        teclado.nextLine();    
        switch (voto) {
            case 1:
                buscarSerie();
                break;
            case 2:
                buscarEpisodio();
                break;
            case 3:
                listagemSeriesBuscadas();
                break;
            case 4:
                buscarSeriePorTitulo();
                break;
            case 5:
                top5Series();
                break;
            case 6:
                buscarSeriePorAtor();
                break;
            case 7:
                buscarSeriesPorCategoria();
                break;
            case 8:
                buscarEpisodioporTrecho();
                break;
            case 9:
                buscarTop5Episodios();
            case 0:
                break;
        }
    
    } while(voto != 0);

    }

    
    public void buscarSerie(){
        System.out.print("Nome da série: ");
        String nomeSerie = teclado.nextLine();
		var json = consumirAPI.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        
        Serie serie = new Serie(dadosSerie);
        repository.save(serie);
        
        
        System.out.println(serie);
    }
    
    public void buscarEpisodio(){
        System.out.println("Lista de Serie");
        listaDeSeries.forEach( serie -> System.out.println(serie.getTitulo()));
        System.out.print("Nome da série: ");
        String nomeSerie = teclado.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);
        if(serie.isPresent()){
            var serieBuscada = serie.get();
            
            List<DadosTemporada> temporadas = new ArrayList<>();
            for ( int i = 1; i <= serieBuscada.gettotal_temporadas() ; i++ ){
                var json = consumirAPI.obterDados(ENDERECO + serieBuscada.getTitulo().toLowerCase().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            
            List<Episodio> episodios = temporadas.stream()
            .flatMap(temporada -> temporada.episodios()
            .stream().map(episodio -> new Episodio(temporada.temporada(), episodio)))
            .collect(Collectors.toList()); 
            serieBuscada.setEpisodios(episodios);
            repository.save(serieBuscada);
            
            episodios.forEach(System.out::println);
            
            
        } else if ( serie.isEmpty()){
            System.out.println("Série não encontrada");
        }
        
        
    }
    
    public void listagemSeriesBuscadas(){
        listaDeSeries = repository.findAll();
        listaDeSeries.stream()
        .sorted(Comparator.comparing(Serie::getGenero))
        .forEach(System.out::println);
        
    }
    
    private void buscarSeriePorTitulo() {
        System.out.print("Nome da série: ");
        String nomeSerie = teclado.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()){
            System.out.println(serie.get().getTitulo() +"\nAvaliação = " + serie.get().getAvaliacao());
        } else if( serie.isEmpty()){
            System.out.println("Série não encontrada");
        }
        
    }
    
    private void top5Series() {
        List<Serie> top5Series = repository.findTop5ByAvaliacaoGreaterThanEqual(8.5);
        top5Series.forEach(serie -> System.out.println(serie.getTitulo() + " - " + serie.getAvaliacao()));
    }
    
    private void buscarSeriePorAtor() {
        System.out.print("Nome do Ator: ");
        String nomeAtor = teclado.nextLine();
        List<Serie> seriesPorAtor = repository.findByAtoresContainingIgnoreCase(nomeAtor);
        seriesPorAtor.forEach( serieAtor -> System.out.println(serieAtor.getAtores() + "\n" + serieAtor.getTitulo() +" - "+ serieAtor.getAvaliacao()));
    }
    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero? ");
        var nomeGenero = teclado.nextLine();
        Genero categoria = Genero.fromPortugues(nomeGenero);
        List<Serie> seriesPorCategoria = repository.findByGenero(categoria);
        System.out.println("Séries da categoria " + nomeGenero);
        seriesPorCategoria.forEach(System.out::println);
    }
    
    private void buscarEpisodioporTrecho() {
        System.out.println("Digite um trecho do título do episódio");
        var trechoTitulo = teclado.nextLine();
        List<Episodio> buscados = repository.episodiosPorTrecho(trechoTitulo);
        buscados.forEach( episodio -> System.out.println(episodio.getSerie().getTitulo() +" - "+ episodio.getTitulo()));
    }
    private void buscarTop5Episodios() {
        System.out.println("Digite nome da Serie");
        var trechoTitulo = teclado.nextLine();
        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(trechoTitulo);
        if (serie.isPresent()){
            List<Episodio> top5Episodios = repository.top5Episodios(serie.get());
            top5Episodios.forEach(System.out::println);
        }
         else if (serie.isEmpty()){
            System.out.println("Série não encontrada");
        }
    }
}