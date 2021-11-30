package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.AgeLimit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgeLimitRepository extends JpaRepository<AgeLimit, String> {
}
