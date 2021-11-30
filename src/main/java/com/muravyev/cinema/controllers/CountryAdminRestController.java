package com.muravyev.cinema.controllers;

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

    @PostMapping("/set")
    public ResponseEntity<?> setCountry(@RequestBody CountryDto countryDto) {
        Country country = countryService.setCountry(countryDto);
        return ResponseEntity.ok(country);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") String code) {
        countryService.deleteCountry(code);
        return ResponseEntity.ok()
                .build();
    }


}
