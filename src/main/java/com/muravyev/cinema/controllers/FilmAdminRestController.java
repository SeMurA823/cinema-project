package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.AddingFilmDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.services.FilmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/films")
public class FilmAdminRestController {
    private FilmService filmService;

    public FilmAdminRestController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/add")
    public Film addFilm(@RequestBody AddingFilmDto filmAddingDto) {
        return filmService.addFilm(filmAddingDto);
    }


}
