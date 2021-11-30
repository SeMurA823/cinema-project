package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;

import java.util.List;

public interface CountryService {
    Country getCountry(String id);

    List<Country> getAllCountries();

    Country setCountry(CountryDto countryDto);

    void deleteCountry(String code);
}
