package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.hall.Hall;

public class DisableHallEvent implements Event<Hall> {
    private final Hall hall;

    public DisableHallEvent(Hall hall) {
        this.hall = hall;
    }

    @Override
    public Hall getValue() {
        return hall;
    }
}
