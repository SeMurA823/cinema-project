package com.muravyev.cinema.controllers.rest;

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

    @GetMapping(value = "/{film}", params = {"start", "end"})
    public ResponseEntity<?> screenings(@PathVariable("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                              @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId, start, end));
    }

    @GetMapping(value = "/{film}")
    public ResponseEntity<?> screenings(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId));
    }

    @GetMapping(value = "/{film}", params = {"start"})
    public ResponseEntity<?> screenings(@PathVariable("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
        return ResponseEntity.ok(screeningService.getFilmScreening(filmId, start));
    }

}
