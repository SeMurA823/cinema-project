package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmRestController {
    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/premieres")
    public Page<Film> premieres(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmService.getPremieres(pageable);
    }

    @GetMapping("/{film}")
    public ResponseEntity<?> film(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.getFilms(List.of(filmId)).get(0));
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getFilms(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.getFilms(id));
    }

    @GetMapping("/archive")
    public Page<Film> archive(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmService.getArchiveFilms(pageable);
    }
}
