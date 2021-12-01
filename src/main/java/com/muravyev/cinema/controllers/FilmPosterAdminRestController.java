package com.muravyev.cinema.controllers;

import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/posters")
public class FilmPosterAdminRestController {
    private final FilmPosterService posterService;

    public FilmPosterAdminRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("film") long filmId) {
        String filename = posterService.save(file, filmId);
        return ResponseEntity.ok(Map.of("filename", filename));
    }

}
