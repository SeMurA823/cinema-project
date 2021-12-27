package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface FilmScreeningService {


    Page<FilmScreening> getAllFilmScreening(long filmId, Pageable pageable);

    void deleteFilmScreenings(List<Long> id);

    List<FilmScreening> getFilmScreeningsInDay(long filmId, Date date);

    FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto);

    FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto);

    List<FilmScreeningSeat> getStatusSeats(long screeningId);

    List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening);

    FilmScreening getFilmScreening(long id);

    void setStatusScreenings(Collection<Long> ids, EntityStatus status);

    Page<Film> getTodayFilms(Pageable pageable);

    Film getFilmByScreening(long screeningId);

    void cancelScreenings(Film film);
}
