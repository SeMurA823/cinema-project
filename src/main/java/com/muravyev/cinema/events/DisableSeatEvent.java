package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.hall.Seat;

public class DisableSeatEvent implements Event<Seat> {
    private final Seat seat;

    public DisableSeatEvent(Seat seat) {
        this.seat = seat;
    }

    @Override
    public Seat getValue() {
        return seat;
    }
}
