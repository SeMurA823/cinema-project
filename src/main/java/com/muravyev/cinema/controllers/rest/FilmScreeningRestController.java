package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.film.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/api/screenings")
public class FilmScreeningRestController {
    private FilmScreeningService screeningService;

    public FilmScreeningRestController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping(value = "/{film}", params = {"start", "end"})
    public Iterable<FilmScreening> screenings(@PathVariable("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                              @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return screeningService.getFilmScreening(filmId, start, end);
    }

    @GetMapping(value = "/{film}")
    public Iterable<FilmScreening> screenings(@PathVariable("film") long filmId) {
        return screeningService.getFilmScreening(filmId);
    }

    @GetMapping(value = "/{film}", params = {"start"})
    public Iterable<FilmScreening> screenings(@PathVariable("film") long filmId,
                                              @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
        return screeningService.getFilmScreening(filmId, start);
    }

}
