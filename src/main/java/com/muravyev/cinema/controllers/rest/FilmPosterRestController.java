package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.PosterDto;
import com.muravyev.cinema.entities.film.FilmPoster;
import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({"/api/posters"})
public class FilmPosterRestController {
    private final FilmPosterService posterService;

    public FilmPosterRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPoster(@PathVariable("id") long id) {
        FilmPoster poster = posterService.getPosters(List.of(id)).stream()
                .findFirst()
                .orElseThrow(EntityExistsException::new);
        return ResponseEntity.ok(poster);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> uploadPoster(@RequestBody @Valid PosterDto posterDto) {
        FilmPoster poster = posterService.createPoster(posterDto);
        return ResponseEntity.ok(poster);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{poster}")
    public ResponseEntity<?> deletePoster(@PathVariable("poster") long posterId) {
        posterService.deletePosters(List.of(posterId));
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(params = "id")
    public ResponseEntity<?> deletePosters(@RequestParam("id") List<Long> postersId) {
        posterService.deletePosters(postersId);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getPosters(@PageableDefault Pageable pageable, @RequestParam("film") long filmId) {
        return ResponseEntity.ok(posterService.getPosters(filmId, pageable));
    }

}
