package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CountryService {
    List<Country> getCountries(List<String> id);

    Page<Country> getAllCountries(Pageable pageable);


    List<Country> setCountries(List<String> code, CountryDto countryDto);

    Country createCountry(CountryDto countryDto);

    void deleteCountries(List<String> code);

    List<Country> getAllCountriesByFilmId(long film);
}
