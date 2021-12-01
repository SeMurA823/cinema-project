package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/films")
public class FilmRestController {
    private final FilmService filmService;

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

    @GetMapping("/archive")
    public Page<Film> archive(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmService.getArchiveFilms(pageable);
    }
}
