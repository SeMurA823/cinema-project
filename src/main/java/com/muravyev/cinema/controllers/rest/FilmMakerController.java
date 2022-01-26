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

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/api/filmmakers"})
public class FilmMakerController {
    private final FilmMakerService makerService;

    public FilmMakerController(FilmMakerService makerService) {
        this.makerService = makerService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid FilmMakerDto filmMakerDto) {
        FilmMaker maker = makerService.createFilmMaker(filmMakerDto);
        return ResponseEntity.ok(maker);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/relate")
    public ResponseEntity<?> relate(@RequestBody @Valid FilmMakerPostDto postDto) {
        FilmMakerPost post = makerService.uploadFilmMakerPost(postDto);
        return ResponseEntity.ok(post);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(params = {"id"})
    public ResponseEntity<?> deleteFilmMakers(@RequestParam("id") Collection<Long> ids) {
        makerService.deleteFilmMakers(ids);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAllMakers(@PageableDefault Pageable pageable) {
        Page<FilmMaker> allFilmMakers = makerService.getAllFilmMakers(pageable);
        return ResponseEntity.ok(allFilmMakers);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<?> setFilmmaker(@PathVariable("id") long id, @RequestBody FilmMakerDto filmMakerDto) {
        FilmMaker maker = makerService.uploadFilmMaker(id, filmMakerDto);
        return ResponseEntity.ok(maker);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(params = {"maker", "film"})
    public ResponseEntity<?> deletePost(@RequestParam("film") long filmId,
                                        @RequestParam("maker") long makerId,
                                        @RequestParam("post") String post) {
        makerService.deleteFilmMakerPost(filmId, makerId, post);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam("film") long filmId) {
        Map<String, List<FilmMaker>> filmMakers = makerService.getFilmMakersPostMap(filmId);
        return ResponseEntity.ok(filmMakers);
    }

    @GetMapping(params = {"search", "page", "size"})
    public ResponseEntity<?> getMakers(@RequestParam("search") String search, @PageableDefault Pageable pageable) {
        Page<FilmMaker> makers = makerService.getFilmMakers(search, pageable);
        return ResponseEntity.ok(makers);
    }
}
