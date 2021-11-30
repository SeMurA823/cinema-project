package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmMaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilmMakerRepository extends JpaRepository<FilmMaker, Long> {
    Optional<FilmMaker> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);
}
