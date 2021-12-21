package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.FilmMakerDto;
import com.muravyev.cinema.dto.FilmMakerPostDto;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import com.muravyev.cinema.services.FilmMakerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping({"/api/filmmakers", "/api/admin/filmmakers"})
public class FilmMakerController {
    private final FilmMakerService makerService;

    public FilmMakerController(FilmMakerService makerService) {
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody FilmMakerDto filmMakerDto) {
        FilmMaker maker = makerService.addFilmMaker(filmMakerDto);
        return ResponseEntity.ok(maker);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/relate")
    public ResponseEntity<?> relate(@RequestBody FilmMakerPostDto postDto) {
        FilmMakerPost post = makerService.setFilmMaker(postDto);
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{maker}/disable")
    public ResponseEntity<?> disableMaker(@PathVariable("maker") long makerId) {
        makerService.disableFilmMaker(makerId);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/{maker}/disable", params = {"film"})
    public ResponseEntity<?> disable(@PathVariable("maker") long makerId, @RequestParam("film") long filmId) {
        makerService.disableFilmMakerPost(filmId, makerId);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{maker}/disable", params = {"film"})
    public ResponseEntity<?> delete(@PathVariable("maker") long makerId, @RequestParam("film") long filmId) {
        makerService.deleteFilmMakerPost(filmId, makerId);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{maker}/disable")
    public ResponseEntity<?> deleteMaker(@PathVariable("maker") long makerId) {
        makerService.disableFilmMaker(makerId);
        return ResponseEntity.ok()
                .build();
    }
}
