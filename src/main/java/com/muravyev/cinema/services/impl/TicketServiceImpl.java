package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.payment.TicketRefund;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.events.*;
import com.muravyev.cinema.events.Observer;
import com.muravyev.cinema.repo.TicketRepository;
import com.muravyev.cinema.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;

@Log4j2
@Service
public class TicketServiceImpl implements TicketService, Observer {

    private final TicketRepository ticketRepository;

    private final Map<Class<? extends Event<?>>, Consumer<Event<?>>> eventActions = new HashMap<>() {{

        put(ReturnPurchaseEvent.class, (event -> returnTickets((Purchase) event.getValue())));

    }};


    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Autowired
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        notificationManager.subscribe(this, eventActions.keySet());
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
    public void cancelTickets(Collection<Long> ids) {
        int amount = ticketRepository.updateStatusAllByIds(ids, EntityStatus.ACTIVE);
        if (amount != ids.size())
            throw new IllegalArgumentException("illegal tickets");
    }

    @Override
    @Transactional
    public void cancelTicket(User user, long ticketId) {
        int amountEdited = ticketRepository.updateStatusByIdAndUserAndEntityStatus(ticketId,
                user,
                EntityStatus.ACTIVE);
        if (amountEdited != 1)
            throw new IllegalArgumentException("illegal ticket");
    }

    @Override
    public Page<Ticket> getTickets(User user, Pageable pageable) {
        return ticketRepository.findAllByPurchaseUserAndFilmScreeningDateAfterAndEntityStatus(user,
                new Date(),
                EntityStatus.ACTIVE,
                pageable);
    }

    private void returnTickets(Purchase purchase) {
        log.info("Purchase: {}", purchase);
        List<Ticket> tickets = ticketRepository.findAllByPurchaseAndEntityStatus(purchase, EntityStatus.ACTIVE);
        tickets.stream()
                .parallel()
                .forEach(this::disableTicket);
    }


    private Ticket disableTicket(Ticket ticket) {
        ticket.setEntityStatus(EntityStatus.NOT_ACTIVE);
        ticket.setTicketRefund(createRefund(ticket));
        return ticketRepository.save(ticket);
    }

    private TicketRefund createRefund(Ticket ticket) {
        TicketRefund refund = new TicketRefund();
        refund.setTicket(ticket);
        return refund;
    }

    @Override
    public void notify(Event<?> event, Class<? extends Event<?>> eventType) {
        eventActions.get(eventType).accept(event);
    }
}
