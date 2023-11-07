package br.com.alura.screenmath.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screenmath.model.Episodio;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
    
}
