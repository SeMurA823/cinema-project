package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.RateDto;
import com.muravyev.cinema.entities.film.FilmRating;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rate")
public class RateController {
    private final RatingService ratingService;

    public RateController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/set")
    public ResponseEntity<?> rate(@RequestBody RateDto rateDto, Authentication authentication) {
        ratingService.setRating(rateDto, (User) authentication.getCredentials());
        return ResponseEntity.ok(rateDto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/get")
    public ResponseEntity<?> getRating(@RequestParam("film") long filmId, Authentication authentication) {
        FilmRating filmRating = ratingService.getRating(filmId, (User) authentication.getPrincipal());
        Map<String, Object> response = Map.of(
                "average", ratingService.getAverage(filmId),
                "customer_rating", filmRating
        );
        return ResponseEntity.ok(response);
    }
}
