package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.FilmScreening;
import com.muravyev.cinema.entities.hall.Place;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationService {
    @Transactional
    Reservation createReservation(FilmScreening filmScreening, User user, Place place);

    @Transactional
    Reservation createReservation(long filmScreeningId, String row, String cell, User user);

    @Transactional
    Reservation createReservation(FilmScreening filmScreening, Customer customer, Place place);

    Page<Reservation> getReservations(User user, Pageable pageable);

    Page<Reservation> getReservations(Customer customer, Pageable pageable);

    void cancelReservation(Reservation reservation);

    void cancelReservation(Customer customer, long reservationId);

    void cancelReservation(User user, long reservationId);
}
