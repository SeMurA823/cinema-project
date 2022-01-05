package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.screening.FilmScreening;

public class CancelScreeningEvent implements Event<FilmScreening> {
    private final FilmScreening filmScreening;

    public CancelScreeningEvent(FilmScreening filmScreening) {
        this.filmScreening = filmScreening;
    }

    @Override
    public FilmScreening getValue() {
        return filmScreening;
    }

    @Override
    public String toString() {
        return "CancelScreeningEvent{" +
                "filmScreening=" + filmScreening +
                '}';
    }
}
