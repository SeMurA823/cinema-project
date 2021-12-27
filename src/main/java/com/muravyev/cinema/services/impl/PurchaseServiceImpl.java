package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.PurchaseRepository;
import com.muravyev.cinema.repo.ReservationRepository;
import com.muravyev.cinema.services.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ReservationRepository reservationRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, ReservationRepository reservationRepository) {
        this.purchaseRepository = purchaseRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Page<Purchase> getAllPurchases(@PageableDefault Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    @Override
    public List<Purchase> cancelPurchases(Iterable<Long> ids) {
        List<Purchase> purchases = purchaseRepository.findAllById(ids);
        purchases.forEach(this::cancelPurchase);
        return purchaseRepository.saveAll(purchases);
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

    private Purchase cancelPurchase(Purchase purchase) {
        purchase.setEntityStatus(EntityStatus.ACTIVE);
        return purchase;
    }
}
