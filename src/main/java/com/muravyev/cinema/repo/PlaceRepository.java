package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.hall.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByRowAndNumberAndHall(String row, String number, Hall hall);
}
