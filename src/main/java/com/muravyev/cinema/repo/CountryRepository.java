package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepository extends JpaRepository<Country, String> {
    List<Country> findAllByFilmsId(Long filmId);
}
