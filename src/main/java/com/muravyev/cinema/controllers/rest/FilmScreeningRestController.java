package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.AddingFilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/screenings")
public class FilmScreeningRestController {
    private FilmScreeningService screeningService;

    public FilmScreeningRestController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping(value = "/", params = {"film","start", "end"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                              @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId, start, end));
    }

    @GetMapping(value = "/", params = {"film"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId));
    }

    @GetMapping(value = "/", params = {"start", "film"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId, start));
    }

    @GetMapping("/{screening}/seats")
    public ResponseEntity<?> seats(@PathVariable("screening") long screeningId) {
        return ResponseEntity.ok(screeningService.getStatusSeats(screeningId));
    }

}
