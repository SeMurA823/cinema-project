package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;

import java.util.List;

public interface CountryService {
    Country findById(long id);

    List<Country> findAll();

    Country save(CountryDto countryDto);
}
