package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.PosterDto;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posters")
public class FilmPosterRestController {
    private final FilmPosterService posterService;

    public FilmPosterRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoster(@PathVariable("id") long id) {
        FilmPoster poster = posterService.getPosters(List.of(id)).get(0);
        return ResponseEntity.ok(poster);
    }

}
