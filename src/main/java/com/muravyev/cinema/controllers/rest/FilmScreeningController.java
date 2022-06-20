package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping({"/api/screenings"})
public class FilmScreeningController {
    private final FilmScreeningService screeningService;

    public FilmScreeningController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"film", "page", "size", "anystatus"})
    public ResponseEntity<?> getAllFilmScreenings(@RequestParam("film") long filmId, @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(screeningService.getAllFilmScreening(filmId, pageable));
    }

    @GetMapping(params = {"film", "start", "end"})
    public ResponseEntity<?> getFilmScreenings(@RequestParam("film") long filmId,
                                               @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
                                               @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end) {
        return ResponseEntity.ok(screeningService.getFilmScreenings(filmId, Date.from(start.toInstant()), Date.from(end.toInstant())));
    }

    @GetMapping(params = {"hall", "start", "end"})
    public ResponseEntity<?> getScheduleFilmScreening(@RequestParam("hall") long hallId,
                                                      @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                      @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        return ResponseEntity.ok(screeningService.getScheduleFilmScreening(hallId, start, end));
    }

    @GetMapping("/{screening}/seats")
    public ResponseEntity<?> seats(@PathVariable("screening") long screeningId) {
        return ResponseEntity.ok(screeningService.getStatusSeats(screeningId));
    }

    @GetMapping("/{screening}/film")
    public ResponseEntity<?> film(@PathVariable("screening") long screeningId) {
        return ResponseEntity.ok(screeningService.getFilmByScreening(screeningId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> addScreening(@RequestBody @Valid FilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = screeningService.createFilmScreening(filmScreeningDto);
        return ResponseEntity.ok(filmScreening);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(params = {"status", "id"})
    public ResponseEntity<?> setStatus(@RequestParam("id") Collection<Long> ids,
                                       @RequestParam("status") EntityStatus status) {
        screeningService.setStatusScreenings(ids, status);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{screening}", params = "anystatus")
    public ResponseEntity<?> getScreening(@PathVariable("screening") long screening) {
        FilmScreening filmScreening = screeningService.getFilmScreening(screening);
        return ResponseEntity.ok(filmScreening);
    }

    @GetMapping(value = "/{screening}")
    public ResponseEntity<?> getActiveScreening(@PathVariable("screening") long screening) {
        FilmScreening filmScreening = screeningService.getFilmScreening(screening);
        return ResponseEntity.ok(filmScreening);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{screening}")
    public ResponseEntity<?> editScreening(@PathVariable("screening") long screening,
                                           @RequestBody @Valid FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = screeningService.setFilmScreening(screening, screeningDto);
        return ResponseEntity.ok(filmScreening);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllScreenings(@RequestParam("film") long filmId, @PageableDefault Pageable pageable) {
        Page<FilmScreening> allFilmScreening = screeningService.getAllFilmScreening(filmId, pageable);
        return ResponseEntity.ok(allFilmScreening);
    }

    @GetMapping("/films")
    public ResponseEntity<?> getFilmsInPeriod(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime start,
                                              @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime end,
                                              @PageableDefault Pageable pageable) {
        Page<Film> todayFilms = screeningService.getFilms(Date.from(start.toInstant()), Date.from(end.toInstant()), pageable);
        return ResponseEntity.ok(todayFilms);
    }
}
