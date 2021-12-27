package com.muravyev.cinema.events.cancel;

import com.muravyev.cinema.entities.film.Film;

public class EditFilmEvent {
    private final Film film;

    public EditFilmEvent(Film film) {
        this.film = film;
    }


    public Film getValue() {
        return film;
    }
}
