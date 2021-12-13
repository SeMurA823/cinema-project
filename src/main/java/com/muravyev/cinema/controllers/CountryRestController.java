package com.muravyev.cinema.controllers;

import com.muravyev.cinema.services.CountryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryRestController {
    private final CountryService countryService;

    public CountryRestController(CountryService countryService) {
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

}
