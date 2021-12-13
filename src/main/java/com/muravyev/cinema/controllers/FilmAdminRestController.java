package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.FilmDto;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/films")
public class FilmAdminRestController {
    private final FilmService filmService;

    public FilmAdminRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFilms(@RequestBody FilmDto filmAddingDto) {
        return ResponseEntity.ok(filmService.addFilm(filmAddingDto));
    }

    @PostMapping("/{film}/disable")
    public ResponseEntity<?> disableFilm(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.disableFilm(filmId));
    }

    @DeleteMapping("/{film}")
    public ResponseEntity<?> deleteFilm(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.deleteFilms(List.of(filmId)).get(0));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFilms(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.deleteFilms(id));
    }

    @PostMapping("/{film}")
    public ResponseEntity<?> setFilm(@PathVariable("film") long id, @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok(filmService.setFilms(List.of(id), filmDto).get(0));
    }

    @PostMapping
    public ResponseEntity<?> setFilms(@RequestParam("id") List<Long> id, @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok(filmService.setFilms(id, filmDto));
    }

    @GetMapping
    public ResponseEntity<?> getFilms(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(filmService.getAllFilms(pageable));
    }
}
