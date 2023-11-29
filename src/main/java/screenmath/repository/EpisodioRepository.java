package screenmath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import screenmath.model.Episodio;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
    
}
