package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.repo.TicketRepository;
import com.muravyev.cinema.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    @Override
    public Page<Ticket> getTickets(long purchaseId, Pageable pageable) {
        return ticketRepository.findAllByPurchaseId(purchaseId, pageable);
    }

    @Override
    @Transactional
    public List<Ticket> cancelTickets(Iterable<Long> ids) {
        List<Ticket> tickets = ticketRepository.findAllById(ids);
        tickets.forEach(this::disableTicket);
        return ticketRepository.saveAll(tickets);
    }

    private Ticket disableTicket(Ticket ticket) {
        ticket.setEntityStatus(EntityStatus.NOT_ACTIVE);
        return ticket;
    }

}
