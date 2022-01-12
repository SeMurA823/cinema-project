package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.hall.Hall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {
    Optional<Hall> findByNameAndEntityStatus(String name, EntityStatus entityStatus);

    Page<Hall> findAllByNameContainsAndEntityStatus(String name, EntityStatus status, Pageable pageable);
}
