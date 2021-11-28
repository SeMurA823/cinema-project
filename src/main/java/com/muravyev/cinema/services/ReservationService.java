package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationService {
    @Transactional
    Reservation createReservation(FilmScreening filmScreening, User user, Seat seat);

    @Transactional
    Reservation createReservation(long filmScreeningId, int row, int cell, User user);

    @Transactional
    Reservation createReservation(FilmScreening filmScreening, Customer customer, Seat seat);

    Page<Reservation> getReservations(User user, Pageable pageable);

    Page<Reservation> getReservations(Customer customer, Pageable pageable);

    void cancelReservation(Reservation reservation);

    void cancelReservation(Customer customer, long reservationId);

    void cancelReservation(User user, long reservationId);
}
