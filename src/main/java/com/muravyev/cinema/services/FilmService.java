package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FilmService {
    Optional<Film> getFilm(Long id);

    Page<Film> getPremieres(Pageable pageable);
}
