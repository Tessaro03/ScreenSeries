package br.com.alura.screenmath.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


import br.com.alura.screenmath.service.ConsultaChatGPT;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Table(name = "series")
@Entity(name = "Series")
public class Serie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;
    private Integer total_temporadas;
    private Double avaliacao;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String atores;
    private String poster;
    private String sinopse;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    
    public Serie(){}
    
    public Serie(DadosSerie dados) {
        this.titulo = dados.titulo();
        this.total_temporadas = dados.total_temporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dados.avaliacao())).orElse(0);
        this.genero = Genero.fromString(dados.genero().split(",")[0].trim());
        this.atores = dados.atores();
        this.poster = dados.poster();
        this.sinopse = ConsultaChatGPT.obterTraducao(dados.sinopse()).trim();
    }
    
    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    } 

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer gettotal_temporadas() {
        return total_temporadas;
    }

    public void settotal_temporadas(Integer total_temporadas) {
        this.total_temporadas = total_temporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @Override
    public String toString() {
        return "titulo = " + titulo + ",\ntotal_temporadas = " + total_temporadas + ",\navaliacao = " + avaliacao
                + "\ngenero = " + genero + "\natores = " + atores + "\nposter = " + poster + "\nsinopse = " + sinopse;
    }

}
