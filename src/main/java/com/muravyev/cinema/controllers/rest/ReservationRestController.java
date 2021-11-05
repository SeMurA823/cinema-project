package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.CreateReservationDto;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reserve")
public class ReservationRestController {
    private ReservationService reservationService;

    public ReservationRestController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/{screening}/create")
    public ResponseEntity<?> reservation(
            @PathVariable("screening") long filmScreeningId,
            @RequestBody CreateReservationDto reservationDto,
            Authentication authentication) {
        Reservation reservation = reservationService.createReservation(
                filmScreeningId,
                reservationDto.getRow(),
                reservationDto.getNum(),
                (User) authentication.getPrincipal());
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/")
    public ResponseEntity<?> reservations(@PageableDefault(sort = "expiryDate") Pageable pageable,
                                          Authentication authentication) {
        Page<Reservation> reservations = reservationService.
                getReservations((User) authentication.getPrincipal(), pageable);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{reserve}/cancel")
    public ResponseEntity<?> cancel(@PathVariable("reserve") long reserveId, Authentication authentication) {
        reservationService.cancelReservation(
                (User) authentication.getPrincipal(),
                reserveId
        );
        return ResponseEntity.ok(Map.of("Status", "Cancelled"));
    }
}
