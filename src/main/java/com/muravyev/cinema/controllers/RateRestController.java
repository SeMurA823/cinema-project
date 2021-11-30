package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.RateDto;
import com.muravyev.cinema.entities.film.FilmRating;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rate")
public class RateRestController {
    private RatingService ratingService;

    public RateRestController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/set")
    public ResponseEntity<?> rate(@RequestBody RateDto rateDto, Authentication authentication) {
        ratingService.setRating(rateDto, (User) authentication.getCredentials());
        return ResponseEntity.ok(rateDto);
    }

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
