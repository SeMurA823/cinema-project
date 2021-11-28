package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.RateDto;
import com.muravyev.cinema.entities.film.FilmRating;
import com.muravyev.cinema.entities.users.User;

public interface RatingService {
    FilmRating setRating(RateDto rateDto, User user);

    FilmRating getRating(long filmId, User principal);

    double getAverage(long filmId);

    void deleteRating(long filmId, User user);
}
