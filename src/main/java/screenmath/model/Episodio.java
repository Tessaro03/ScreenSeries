package screenmath.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Episodios")
@Table(name = "episodios")
public class Episodio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Integer episodio;
    private Double avaliacao;
    private Integer temporada;
    private LocalDate dataLancamento;

    @ManyToOne
    private Serie serie;

    public Episodio(){}

    public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
        this.titulo = dadosEpisodio.titulo();
        this.episodio = dadosEpisodio.episodio();
        try{
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException ex){
            this.avaliacao = 0.0;
        }
        this.temporada = temporada;
        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (DateTimeParseException de){
            this.dataLancamento = null;
        }
    }
    
    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getEpisodio() {
        return episodio;
    }
    public void setEpisodio(Integer episodio) {
        this.episodio = episodio;
    }
    public Double getAvaliacao() {
        return avaliacao;
    }
    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }
    public Integer getTemporada() {
        return temporada;
    }
    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }
    public LocalDate getDataLancamento() {
        return dataLancamento;
    }
    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }
    
    @Override
    public String toString() {
        return "Titulo = " + titulo + ", Episodio = " + episodio + ", Avaliacao = " + avaliacao + ", Temporada = "
                + temporada + ", Data Lançaamento = " + dataLancamento ;
    }
    
}