package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
