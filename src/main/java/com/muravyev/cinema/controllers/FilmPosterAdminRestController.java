package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/posters")
public class FilmPosterAdminRestController {
    private final FilmPosterService posterService;

    public FilmPosterAdminRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPoster(@RequestParam("file") MultipartFile file, @RequestParam("film") long filmId) {
        FilmPoster poster = posterService.save(file, filmId);
        return ResponseEntity.ok(poster);
    }

    @DeleteMapping("{poster}/delete")
    public ResponseEntity<?> deletePoster(@PathVariable("poster") long posterId) {
        posterService.delete(posterId);
        return ResponseEntity.ok()
                .build();
    }

}
