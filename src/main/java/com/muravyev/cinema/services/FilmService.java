package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.AddingFilmDto;
import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmService {
    Film getFilm(Long id);

    Page<Film> getPremieres(Pageable pageable);

    Film addFilm(AddingFilmDto addingFilmDto);

    Page<Film> getArchiveFilms(Pageable pageable);

    void deleteFilm(long filmId);

    Film disableFilm(long filmId);
}
