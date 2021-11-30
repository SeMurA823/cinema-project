package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.repo.CountryRepository;
import com.muravyev.cinema.services.CountryService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getCountry(String id) {
        return countryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll(Sort.by("code").ascending());
    }

    @Override
    public Country setCountry(CountryDto countryDto) {
        Country country = countryRepository.findById(countryDto.getCode())
                .orElse(new Country());
        country.setCode(countryDto.getCode());
        country.setFullName(countryDto.getFullName());
        country.setShortName(countryDto.getShortName());
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }

}
