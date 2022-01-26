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

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/films"})
public class FilmRestController {
    private final FilmService filmService;

    public FilmRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(value = "/premieres")
    public ResponseEntity<?> getPremieres(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        Page<Film> premieres = filmService.getPremieres(pageable);
        return ResponseEntity.ok(premieres);
    }

    @GetMapping("/{film}")
    public ResponseEntity<?> getActiveFilm(@PathVariable("film") long filmId) {
        Film film = filmService.getActiveFilms(List.of(filmId)).stream()
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
        return ResponseEntity.ok(film);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{film}", params = {"anystatus"})
    public ResponseEntity<?> getAllFilms(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.getAllFilms(List.of(filmId)).get(0));
    }

    @GetMapping(params = "id")
    public ResponseEntity<?> getActiveFilms(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.getActiveFilms(id));
    }

    @GetMapping(params = {"id", "anystatus"})
    public ResponseEntity<?> getFilmsAnyStatus(@RequestParam("id") List<Long> id) {
        return ResponseEntity.ok(filmService.getAllFilms(id));
    }

    @GetMapping("/archive")
    public ResponseEntity<?> getArchive(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        Page<Film> archiveFilms = filmService.getArchiveFilms(pageable);
        return ResponseEntity.ok(archiveFilms);
    }

    //admin

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createFilms(@RequestBody @Valid FilmDto filmAddingDto) {
        return ResponseEntity.ok(filmService.saveFilm(filmAddingDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{film}/disable")
    public ResponseEntity<?> disableFilm(@PathVariable("film") long filmId) {
        return ResponseEntity.ok(filmService.disableFilm(filmId));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{film}")
//    public ResponseEntity<?> deleteFilm(@PathVariable("film") long filmId) {
//        return ResponseEntity.ok(filmService.deleteFilms(List.of(filmId)).get(0));
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping
//    public ResponseEntity<?> deleteFilms(@RequestParam("id") List<Long> id) {
//        return ResponseEntity.ok(filmService.deleteFilms(id));
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{film}")
    public ResponseEntity<?> updateFilm(@PathVariable("film") long id, @RequestBody @Valid FilmDto filmDto) {
        return ResponseEntity.ok(filmService.updateFilms(id, filmDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> updateFilm(@RequestParam("id") List<Long> id, @RequestBody @Valid FilmDto filmDto) {
        return ResponseEntity.ok(filmService.updateFilms(id, filmDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllFilms(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(filmService.getAllFilms(pageable));
    }

}
