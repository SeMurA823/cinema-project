package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.FilmDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/films"})
public class FilmRestController {
    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/premieres")
    public ResponseEntity<?> premieres(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        Page<Film> premieres = filmService.getPremieres(pageable);
        return ResponseEntity.ok(premieres);
    }

    @GetMapping("/{film}")
    public ResponseEntity<?> film(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.getFilms(List.of(filmId)).get(0));
    }

    @GetMapping(value = "/{film}", params = {"anystatus"})
    public ResponseEntity<?> filmAnyStatus(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.getFilmsAnyStatus(List.of(filmId)).get(0));
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getFilms(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.getFilms(id));
    }

    @GetMapping(params = {"id", "anystatus"})
    public ResponseEntity<?> getFilmsAnyStatus(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.getFilmsAnyStatus(id));
    }

    @GetMapping("/archive")
    public ResponseEntity<?> archive(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        Page<Film> archiveFilms = filmService.getArchiveFilms(pageable);
        return ResponseEntity.ok(archiveFilms);
    }

    //admin

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createFilms(@RequestBody FilmDto filmAddingDto) {
        return ResponseEntity.ok(filmService.addFilm(filmAddingDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{film}/disable")
    public ResponseEntity<?> disableFilm(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.disableFilm(filmId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{film}")
    public ResponseEntity<?> deleteFilm(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.deleteFilms(List.of(filmId)).get(0));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteFilms(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.deleteFilms(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{film}")
    public ResponseEntity<?> setFilm(@PathVariable("film") long id, @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok(filmService.setFilms(List.of(id), filmDto).get(0));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> setFilms(@RequestParam("id") List<Long> id, @RequestBody FilmDto filmDto) {
        return ResponseEntity.ok(filmService.setFilms(id, filmDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getFilms(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(filmService.getAllFilms(pageable));
    }


}
