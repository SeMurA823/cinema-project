package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.ReservationDto;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/create")
    public ResponseEntity<?> createReserv(@RequestBody @Valid @NotEmpty List<ReservationDto> reservationDtos,
                                          Authentication authentication) {
        List<Reservation> reservations = reservationService.createReservations(reservationDtos,
                (User) authentication.getPrincipal());
        return ResponseEntity.ok(reservations);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/mybooking/screenings", params = {"film"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId, Authentication authentication) {
        List<FilmScreening> screenings = reservationService.getFilmScreeningWhereMyBookings(filmId,
                (User) authentication.getPrincipal());
        return ResponseEntity.ok(screenings);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/mybooking", params = {"screening"})
    public ResponseEntity<?> getBookings(@RequestParam("screening") long screeningId, Authentication authentication) {
        List<Reservation> reservations = reservationService.getReservations(screeningId,
                (User) authentication.getPrincipal());
        return ResponseEntity.ok(reservations);
    }
}
