package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.dto.ScreeningTime;
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

    List<FilmScreening> getFilmScreenings(long filmId, Date start, Date end);

    FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto);

    FilmScreening createFilmScreening(FilmScreeningDto filmScreeningDto);

    List<FilmScreeningSeat> getStatusSeats(long screeningId);

    List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening);

    FilmScreening getFilmScreening(long id);

    void setStatusScreenings(Collection<Long> ids, EntityStatus status);

    Page<Film> getFilms(Date start, Date end, Pageable pageable);

    Film getFilmByScreening(long screeningId);

    List<ScreeningTime> getScheduleFilmScreening(long hallId, Date start, Date end);
}
