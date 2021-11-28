package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.hall.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {
}
