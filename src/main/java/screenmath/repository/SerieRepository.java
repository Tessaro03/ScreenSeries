package screenmath.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import screenmath.model.Episodio;
import screenmath.model.Genero;
import screenmath.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long>{
    
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    
    List<Serie> findTop5ByAvaliacaoGreaterThanEqual(Double valor);


    List<Serie> findByAtoresContainingIgnoreCase(String nomeAtor);

    List<Serie> findByGenero(Genero genero);


    @Query("SELECT e FROM Series s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);


    @Query("SELECT e FROM Series s Join s.episodios e WHERE s = :serieBuscada ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> top5Episodios(Serie serieBuscada);
}
