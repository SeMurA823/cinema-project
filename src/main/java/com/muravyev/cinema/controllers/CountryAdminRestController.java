package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.services.CountryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/countries")
public class CountryAdminRestController {
    private final CountryService countryService;

    public CountryAdminRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto) {
        Country country = countryService.createCountry(countryDto);
        return ResponseEntity.ok(country);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> setCountry(@PathVariable("id") String code, @RequestBody CountryDto countryDto) {
        countryDto.setId(code);
        Country country = countryService.setCountries(List.of(code), countryDto).get(0);
        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") String code) {
        deleteCountries(List.of(code));
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCountries(@RequestParam("id") List<String> codes) {
        countryService.deleteCountries(codes);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(countryService.getAllCountries(pageable));
    }

    @GetMapping(params = {"film"})
    public ResponseEntity<?> getAllByCountry(@RequestParam("film") long film) {
        return ResponseEntity.ok(countryService.getAllCountriesByFilmId(film));
    }


}
