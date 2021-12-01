package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.AgeLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgeLimitRepository extends JpaRepository<AgeLimit, String> {
}
