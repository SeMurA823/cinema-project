package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.payment.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
