package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.services.CountryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/countries")
public class CountryAdminRestController {
    private CountryService countryService;

    public CountryAdminRestController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CountryDto countryDto) {
        Country country = countryService.save(countryDto);
        return ResponseEntity.ok(country);
    }
}
