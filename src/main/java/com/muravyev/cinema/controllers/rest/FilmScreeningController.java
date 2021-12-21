package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping({"/api/screenings", "/api/admin/screening"})
public class FilmScreeningController {
    private final FilmScreeningService screeningService;

    public FilmScreeningController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping(value = "/", params = {"film", "start", "end"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId,
                                        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
                                        @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {
        return ResponseEntity.ok(screeningService.getFilmScreenings(filmId, start, end));
    }

    @GetMapping(value = "/", params = {"film"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId) {
        return ResponseEntity.ok(screeningService.getFilmScreenings(filmId));
    }

    @GetMapping(value = "/", params = {"start", "film"})
    public ResponseEntity<?> screenings(@RequestParam("film") long filmId,
                                        @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd") Date start) {
        return ResponseEntity.ok(screeningService.getFilmScreenings(filmId, start));
    }

    @GetMapping("/{screening}/seats")
    public ResponseEntity<?> seats(@PathVariable("screening") long screeningId) {
        return ResponseEntity.ok(screeningService.getStatusSeats(screeningId));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> addScreening(@RequestBody FilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = screeningService.addFilmScreening(filmScreeningDto);
        return ResponseEntity.ok(filmScreening);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{screening}/disable")
    public ResponseEntity<?> disableScreening(@PathVariable("screening") long screening) {
        screeningService.disableFilmScreening(screening);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{screening}")
    public ResponseEntity<?> editScreening(@PathVariable("screening") long screening,
                                           @RequestBody FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = screeningService.setFilmScreening(screening, screeningDto);
        return ResponseEntity.ok(filmScreening);
    }
}
