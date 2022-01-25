package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface TicketService {

    Page<Ticket> getAllTickets(Pageable pageable);

    Page<Ticket> getTickets(long purchaseId, Pageable pageable);

    void cancelTickets(Collection<Long> ids);

    void returnTicket(User user, long ticketId);

    Page<Ticket> getTickets(User user, Pageable pageable);
}
