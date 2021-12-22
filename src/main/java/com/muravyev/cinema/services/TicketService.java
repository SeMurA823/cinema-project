package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.payment.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    Page<Ticket> getAllTickets(Pageable pageable);

    Page<Ticket> getTickets(long purchaseId, Pageable pageable);

    List<Ticket> cancelTickets(Iterable<Long> ids);
}
