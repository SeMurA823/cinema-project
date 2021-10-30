package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.repo.FilmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class FilmRestController {
    private FilmRepository filmRepository;

    public FilmRestController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/premieres")
    public Page<Film> premieres(@PageableDefault(sort = "localPremiere") Pageable pageable) {
        return filmRepository.comingPremieres(pageable);
    }
}
