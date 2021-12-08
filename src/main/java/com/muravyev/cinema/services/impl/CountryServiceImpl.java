package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.repo.CountryRepository;
import com.muravyev.cinema.services.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> getCountries(List<String> id) {
        return countryRepository.findAllById(id);
    }

    @Override
    public Page<Country> getAllCountries(Pageable pageable) {
        return countryRepository.findAll(pageable);
    }

    @Override
    public List<Country> setCountries(List<String> code, CountryDto countryDto) {
        List<Country> allById = countryRepository.findAllById(code);
        allById.forEach(x -> setCountry(countryDto, x));
        return countryRepository.saveAll(allById);
    }

    @Override
    public Country createCountry(CountryDto countryDto) {
        Country country = setCountry(countryDto, new Country());
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountries(List<String> codes) {
        countryRepository.deleteAllById(codes);
    }

    @Override
    public List<Country> getAllCountriesByFilmId(long film) {
        return countryRepository.findAllByFilmsId(film);
    }

    private Country setCountry(CountryDto countryDto, Country country) {
        country.setId(countryDto.getId());
        country.setFullName(countryDto.getFullName());
        country.setShortName(countryDto.getShortName());
        return country;
    }

}
