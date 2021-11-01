package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere > CURRENT_DATE AND f.entityStatus = 'ENABLE' ")
    Page<Film> getComingPremieres(Pageable pageable);

    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere < CURRENT_DATE AND f.entityStatus = 'ENABLE'")
    Page<Film> getArchiveFilms(Pageable pageable);
}
