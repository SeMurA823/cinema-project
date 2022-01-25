package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.payment.TicketRefund;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.events.Observable;
import com.muravyev.cinema.events.Observer;
import com.muravyev.cinema.events.*;
import com.muravyev.cinema.repo.TicketRepository;
import com.muravyev.cinema.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;

@Log4j2
@Service
public class TicketServiceImpl implements TicketService, Observer, Observable {

    @Value("${app.refund.limit-time}")
    private long limitReturnTicketDays;

    private final TicketRepository ticketRepository;
    private final MessageSource messageSource;

    private NotificationManager notificationManager;


    private final Map<Class<? extends Event<?>>, Consumer<Event<?>>> eventActions = new HashMap<>() {{
        put(ReturnPurchaseEvent.class, (event -> returnTickets((Purchase) event.getValue())));
        put(DisableSeatEvent.class, (event -> returnTickets(((DisableSeatEvent) event).getValue())));
    }};

    public TicketServiceImpl(TicketRepository ticketRepository,
                             MessageSource messageSource) {
        this.ticketRepository = ticketRepository;
        this.messageSource = messageSource;
    }

    @Autowired
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
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
        List<Ticket> tickets = ticketRepository.findAllByIdInAndEntityStatus(ids, EntityStatus.ACTIVE);
        if (tickets.size() != ids.size())
            throw new IllegalArgumentException("illegal tickets");
        tickets.forEach(this::disableTicket);
    }

    @Override
    @Transactional
    public void returnTicket(User user, long ticketId) {
        Ticket ticket = ticketRepository.findByIdAndPurchaseUserAndEntityStatus(ticketId, user, EntityStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("illegal ticket"));
        FilmScreening filmScreening = ticket.getFilmScreening();
        Instant limit = ZonedDateTime.now().plusDays(limitReturnTicketDays).toInstant();
        if (filmScreening.getDate().after(Date.from(limit)))
            throw new IllegalArgumentException("Refund is not possible");
        disableTicket(ticket);
    }

    @Override
    public Page<Ticket> getTickets(User user, Pageable pageable) {
        return ticketRepository.findAllByPurchaseUserAndFilmScreeningDateAfterAndEntityStatus(user,
                new Date(),
                EntityStatus.ACTIVE,
                pageable);
    }

    private void returnTickets(Purchase purchase) {
        List<Ticket> tickets = ticketRepository.findAllByPurchaseAndEntityStatus(purchase, EntityStatus.ACTIVE);
        tickets.forEach(this::disableTicket);
    }

    private void returnTickets(Seat seat) {
        List<Ticket> tickets = ticketRepository.findAllBySeatAndEntityStatus(seat, EntityStatus.ACTIVE);
        tickets.forEach(this::disableTicket);
    }


    private Ticket disableTicket(Ticket ticket) {
        if (ticket.isExpired())
            throw new IllegalStateException("Ticket expired");
        ticket.setEntityStatus(EntityStatus.NOT_ACTIVE);
        ticket.setTicketRefund(createRefund(ticket));
//        log.info("User {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Ticket savedTicket = ticketRepository.save(ticket);
        User user = ticket.getPurchase().getUser();
        notificationManager.notify(new ReturnTicketEvent(Map.of(user.getId(),
                        messageSource.getMessage("ticket.canceled",
                                new Object[]{ticket.getId()},
                                Locale.getDefault())),
                        savedTicket),
                ReturnTicketEvent.class);
        return savedTicket;
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
