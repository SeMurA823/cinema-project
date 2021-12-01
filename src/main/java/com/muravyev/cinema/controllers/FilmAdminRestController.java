package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.AddingFilmDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/films")
public class FilmAdminRestController {
    private FilmService filmService;

    public FilmAdminRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFilm(@RequestBody AddingFilmDto filmAddingDto) {
        Film film = filmService.addFilm(filmAddingDto);
        return ResponseEntity.ok(film);
    }

    @PostMapping("/{film}/disable")
    public ResponseEntity<?> disableFilm(@PathVariable("film") long filmId) {
        Film film = filmService.disableFilm(filmId);
        return ResponseEntity.ok(film);
    }

    @DeleteMapping("/{film}/delete")
    public ResponseEntity<?> deleteFilm(@PathVariable("film") long filmId) {
        filmService.deleteFilm(filmId);
        return ResponseEntity.ok()
                .build();
    }


}
