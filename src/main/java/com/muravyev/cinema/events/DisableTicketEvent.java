package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.payment.Ticket;

import java.util.Map;

public class DisableTicketEvent extends UserEvent<Ticket> {

    private final Ticket ticket;

    public DisableTicketEvent(Map<Long, String> messages, Ticket ticket) {
        super(messages);
        this.ticket = ticket;
    }

    @Override
    public Ticket getValue() {
        return ticket;
    }
}
