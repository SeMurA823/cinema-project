package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.FilmMakerDto;
import com.muravyev.cinema.dto.FilmMakerPostDto;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import com.muravyev.cinema.services.FilmMakerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/filmmakers")
public class FilmMakerAdminRestController {
    private final FilmMakerService makerService;

    public FilmMakerAdminRestController(FilmMakerService makerService) {
        this.makerService = makerService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody FilmMakerDto filmMakerDto) {
        FilmMaker maker = makerService.addFilmMaker(filmMakerDto);
        return ResponseEntity.ok(maker);
    }

    @PostMapping("/relate")
    public ResponseEntity<?> relate(@RequestBody FilmMakerPostDto postDto) {
        FilmMakerPost post = makerService.setFilmMaker(postDto);
        return ResponseEntity.ok(post);
    }

    @PostMapping("/{maker}/disable")
    public ResponseEntity<?> disableMaker (@PathVariable("maker") long makerId) {
        makerService.disableFilmMaker(makerId);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping(value = "/{maker}/disable", params = {"film"})
    public ResponseEntity<?> disable(@PathVariable("maker") long makerId, @RequestParam("film") long filmId) {
        makerService.disableFilmMakerPost(filmId, makerId);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping(value = "/{maker}/disable", params = {"film"})
    public ResponseEntity<?> delete(@PathVariable("maker") long makerId, @RequestParam("film") long filmId) {
        makerService.deleteFilmMakerPost(filmId, makerId);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{maker}/disable")
    public ResponseEntity<?> deleteMaker (@PathVariable("maker") long makerId) {
        makerService.disableFilmMaker(makerId);
        return ResponseEntity.ok()
                .build();
    }

}
