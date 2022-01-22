package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.ReservationDto;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;

import java.util.List;

public interface ReservationService {

    List<Reservation> createReservations(List<ReservationDto> reservationDtos, User user);

    List<Reservation> getReservations(long screeningId, User user);

    List<FilmScreening> getFilmScreeningWhereMyBookings(long filmId, User user);
}
