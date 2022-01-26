package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmDto;
import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FilmService {
    List<Film> getActiveFilms(List<Long> id);

    List<Film> getAllFilms(List<Long> id);

    Page<Film> getPremieres(Pageable pageable);

    Film saveFilm(FilmDto addingFilmDto);

    Page<Film> getArchiveFilms(Pageable pageable);

    List<Film> deleteFilms(List<Long> id);

    Film disableFilm(long filmId);

    Page<Film> getAllFilms(Pageable pageable);

    List<Film> updateFilms(List<Long> filmId, FilmDto filmDto);

    @Transactional
    Film updateFilms(long id, FilmDto filmDto);
}
