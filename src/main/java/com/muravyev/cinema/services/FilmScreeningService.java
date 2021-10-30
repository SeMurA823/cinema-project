package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmScreening;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

public interface FilmScreeningService {



    Collection<FilmScreening> getFilmScreening(long filmId, Date start, Date end);

    Collection<FilmScreening> getFilmScreening(long filmId, Date start);

    Collection<FilmScreening> getFilmScreening(long filmId);
}
