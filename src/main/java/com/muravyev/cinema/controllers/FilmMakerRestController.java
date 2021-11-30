package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import com.muravyev.cinema.services.FilmMakerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/filmmakers")
public class FilmMakerRestController {
    private final FilmMakerService makerService;

    public FilmMakerRestController(FilmMakerService makerService) {
        this.makerService = makerService;
    }

    @GetMapping(params = {"film"})
    public ResponseEntity<?> getMakers(@RequestParam("film") long filmId) {
        Map<String, List<FilmMakerPost>> posts = makerService.getFilmMakerPosts(filmId).stream()
                .collect(Collectors.groupingBy(FilmMakerPost::getName));
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{maker}")
    public ResponseEntity<?> getMaker(@PathVariable("maker") long makerId) {
        FilmMaker maker = makerService.getFilmMaker(makerId);
        return ResponseEntity.ok(maker);
    }

    @GetMapping(value = "/{maker}/completed")
    public ResponseEntity<?> getPosts(@PathVariable("maker") long makerId,
                                      @PageableDefault(sort = {"film.localPremiere"}) Pageable pageable) {
        Page<FilmMakerPost> posts = makerService.getAllPosts(makerId, pageable);
        return ResponseEntity.ok(posts);
    }
}
