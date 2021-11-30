package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface FilmScreeningService {



    Collection<FilmScreening> getFilmScreening(long filmId, Date start, Date end);

    Collection<FilmScreening> getFilmScreening(long filmId, Date start);

    Collection<FilmScreening> getFilmScreening(long filmId);

    void disableFilmScreening(long filmScreening);

    FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto);

    FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto);

    List<FilmScreeningSeat> getStatusSeats(long screeningId);

    List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening);
}
