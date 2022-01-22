package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping(params = {"film"})
    public ResponseEntity<?> getRating(@RequestParam("film") long filmId) {
        return ResponseEntity.ok(ratingService.getRating(filmId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping(value = "/mark",params = {"film"})
    public ResponseEntity<?> getMark(@RequestParam("film") long filmId, Authentication authentication) {
        return ResponseEntity.ok(ratingService.getMark(filmId, (User) authentication.getPrincipal()));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping(value = "/mark",params = {"film"})
    public ResponseEntity<?> deleteMark(@RequestParam("film") long filmId, Authentication authentication) {
        ratingService.deleteMark(filmId, (User) authentication.getPrincipal());
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping(value = "/mark", params = {"film"})
    public ResponseEntity<?> setMark(@RequestParam("film") long filmId, @RequestBody @Valid @Min(1) @Max(10) int mark, Authentication authentication) {
        return ResponseEntity.ok(ratingService.setMark(mark, filmId, (User) authentication.getPrincipal()));
    }

}
