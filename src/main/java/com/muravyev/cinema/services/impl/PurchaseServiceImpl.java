package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.events.*;
import com.muravyev.cinema.events.Observable;
import com.muravyev.cinema.events.Observer;
import com.muravyev.cinema.repo.PurchaseRepository;
import com.muravyev.cinema.repo.ReservationRepository;
import com.muravyev.cinema.services.NotificationService;
import com.muravyev.cinema.services.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PurchaseServiceImpl implements PurchaseService, Observer, Observable {
    private final PurchaseRepository purchaseRepository;
    private final ReservationRepository reservationRepository;
    private final NotificationService notificationService;
    private final MessageSource messageSource;

    private final Map<Class<? extends Event<?>>, Consumer<Event<?>>> eventActions = new HashMap<>() {{

        put(CancelScreeningEvent.class, event -> cancelPurchases(((CancelScreeningEvent) event).getValue()));

    }};

    private NotificationManager notificationManager;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository,
                               ReservationRepository reservationRepository,
                               NotificationService notificationService, MessageSource messageSource) {
        this.purchaseRepository = purchaseRepository;
        this.reservationRepository = reservationRepository;
        this.notificationService = notificationService;
        this.messageSource = messageSource;
    }

    @Override
    public void notify(Event<?> event, Class<? extends Event<?>> eventType) {
        eventActions.get(eventType).accept(event);
    }

    @Autowired
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
        notificationManager.subscribe(this, eventActions.keySet());
    }

    @Override
    public Page<Purchase> getAllPurchases(@PageableDefault Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    private void cancelPurchases(FilmScreening screening) {
        List<Purchase> purchases = purchaseRepository.findAllByScreeningAndEntityStatus(screening, EntityStatus.ACTIVE);
        cancelPurchases(purchases);
    }

    @Override
    @Transactional
    public void cancelPurchasesById(List<Long> ids) {
        List<Purchase> purchases = purchaseRepository.findAllByIdInAndEntityStatus(ids, EntityStatus.ACTIVE);
        cancelPurchases(purchases);
    }

    @Override
    public void cancelPurchases(List<Purchase> purchases) {
        purchases.forEach(purchase -> purchase.setEntityStatus(EntityStatus.NOT_ACTIVE));
        List<Purchase> savedPurchases = purchaseRepository.saveAll(purchases);
        if (savedPurchases.size() != purchases.size())
            throw new IllegalArgumentException("invalid purchases");

        savedPurchases.stream()
                .parallel()
                .peek(x -> notificationService.notifyUser(messageSource.getMessage("purchase.canceled",
                        new Object[]{x.getId()},
                        Locale.getDefault()), x.getUser()))
                .forEach(x -> notificationManager.notify(new ReturnPurchaseEvent(x), ReturnPurchaseEvent.class));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Purchase buy(Collection<Long> reservationId, User user) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        List<Reservation> reservations = reservationRepository.findAllByIdInAndUserAndEntityStatus(reservationId,
                user,
                EntityStatus.ACTIVE);
        if (reservations.size() != reservationId.size())
            throw new IllegalArgumentException("the values do not match");
        List<Ticket> collect = reservations.stream()
                .peek(x -> x.setEntityStatus(EntityStatus.NOT_ACTIVE))
                .map(this::createTicket)
                .peek(x -> x.setPurchase(purchase))
                .collect(Collectors.toList());
        purchase.setTickets(collect);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        reservationRepository.saveAll(reservations);
        return savedPurchase;
    }

    private Ticket createTicket(Reservation reservation) {
        Ticket ticket = new Ticket();
        ticket.setFilmScreening(reservation.getFilmScreening());
        ticket.setSeat(reservation.getSeat());
        ticket.setPrice(reservation.getFilmScreening().getPrice());
        return ticket;
    }
}
