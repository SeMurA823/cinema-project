package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.FilmMark;
import com.muravyev.cinema.entities.users.User;

public interface RatingService {
    FilmMark setMark(int mark, long filmId, User user);

    double getRating(long filmId);

    FilmMark getMark(long filmId, User user);
}
