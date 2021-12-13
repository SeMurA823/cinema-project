package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.PosterDto;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posters")
public class FilmPosterAdminRestController {
    private final FilmPosterService posterService;

    public FilmPosterAdminRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> uploadPoster(@RequestBody PosterDto posterDto) {
        FilmPoster poster = posterService.createPoster(posterDto);
        return ResponseEntity.ok(poster);
    }

    @DeleteMapping("{poster}")
    public ResponseEntity<?> deletePoster(@PathVariable("poster") long posterId) {
        posterService.delete(List.of(posterId));
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping(params = "id")
    public ResponseEntity<?> deletePosters(@RequestParam("id") List<Long> postersId) {
        posterService.delete(postersId);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoster(@PathVariable("id") long posterId) {
        return ResponseEntity.ok(posterService.getPosters(List.of(posterId)).get(0));
    }

    @GetMapping
    public ResponseEntity<?> getPosters(@PageableDefault Pageable pageable, @RequestParam("film") long filmId) {
        return ResponseEntity.ok(posterService.getPosters(filmId, pageable));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updatePoster(@PathVariable("id") long posterId, @RequestBody PosterDto posterDto) {
        return ResponseEntity.ok(posterService.updatePoster(posterId, posterDto));
    }
}
