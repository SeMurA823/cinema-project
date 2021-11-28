package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByRowAndNumberAndHall(int row, int number, Hall hall);
}
