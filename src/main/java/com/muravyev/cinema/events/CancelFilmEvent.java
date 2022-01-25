package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.film.Film;

public class CancelFilmEvent implements Event<Film> {
    private final Film film;

    public CancelFilmEvent(Film film) {
        this.film = film;
    }

    @Override
    public Film getValue() {
        return film;
    }

}
