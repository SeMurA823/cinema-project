package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmDto;
import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmService {
    List<Film> getFilms(List<Long> id);

    List<Film> getFilmsAnyStatus(List<Long> id);

    Page<Film> getPremieres(Pageable pageable);

    Film addFilm(FilmDto addingFilmDto);

    Page<Film> getArchiveFilms(Pageable pageable);

    List<Film> deleteFilms(List<Long> id);

    Film disableFilm(long filmId);

    Page<Film> getAllFilms(Pageable pageable);

    List<Film> setFilms(List<Long> filmId, FilmDto filmDto);
}
