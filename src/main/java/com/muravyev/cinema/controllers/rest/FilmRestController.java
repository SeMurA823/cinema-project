package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.AddingFilmDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/films")
public class FilmRestController {
    private FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/premieres", params = {"size", "page"})
    public Page<Film> premieres(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmService.getPremieres(pageable);
    }

    @GetMapping("/{film}")
    public Film film(@PathVariable("film") Long filmId) {
        return filmService.getFilm(filmId);
    }

    @GetMapping("/add")
    public Film addFilm(@RequestBody AddingFilmDto filmAddingDto) {
        return filmService.addFilm(filmAddingDto);
    }

    @GetMapping("/archive")
    public Page<Film> archive(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmService.getArchiveFilms(pageable);
    }

    @DeleteMapping("/{film}/delete")
    public ResponseEntity<?> delete(@PathVariable("film") long filmId) {
        filmService.deleteFilm(filmId);
        HashMap<Object, Object> response = new HashMap<>();
        response.put("Status", "Deleted");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{film}/disable")
    public ResponseEntity<?> disable(@PathVariable("film") long filmId) {
        filmService.disableFilm(filmId);
        HashMap<Object, Object> response = new HashMap<>();
        response.put("Status", "Disabled");
        return ResponseEntity.ok(response);
    }
}
