package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.CountryDto;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.services.CountryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/countries", "/api/admin/countries"})
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<?>> getAll() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String country) {
        return ResponseEntity.ok(countryService.getCountries(List.of(country)).get(0));
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getAllById(@RequestParam("id") List<String> codes) {
        return ResponseEntity.ok(countryService.getCountries(codes));
    }

    @GetMapping(params = {"size", "page"})
    public ResponseEntity<?> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(countryService.getAllCountries(pageable));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto) {
        Country country = countryService.createCountry(countryDto);
        return ResponseEntity.ok(country);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<?> setCountry(@PathVariable("id") String code, @RequestBody CountryDto countryDto) {
        countryDto.setId(code);
        Country country = countryService.setCountries(List.of(code), countryDto).get(0);
        return ResponseEntity.ok(country);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") String code) {
        deleteCountries(List.of(code));
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteCountries(@RequestParam("id") List<String> codes) {
        countryService.deleteCountries(codes);
        return ResponseEntity.ok()
                .build();
    }

}
