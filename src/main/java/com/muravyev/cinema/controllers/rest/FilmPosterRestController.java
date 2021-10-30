package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posters")
public class FilmPosterRestController {
    private FilmPosterService posterService;

    public FilmPosterRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @PostMapping("/{film}")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @PathVariable("film") long filmId) {
        String filename = posterService.save(file, filmId);
        return ResponseEntity.ok(filename);
    }
}
