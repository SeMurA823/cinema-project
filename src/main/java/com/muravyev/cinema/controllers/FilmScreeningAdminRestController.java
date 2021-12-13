package com.muravyev.cinema.controllers;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/screenings")
public class FilmScreeningAdminRestController {
    private FilmScreeningService screeningService;

    public FilmScreeningAdminRestController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> addScreening(@RequestBody FilmScreeningDto filmScreeningDto){
        FilmScreening filmScreening = screeningService.addFilmScreening(filmScreeningDto);
        return ResponseEntity.ok(filmScreening);
    }

    @PostMapping("/{screening}/disable")
    public ResponseEntity<?> disableScreening(@PathVariable("screening") long screening) {
        screeningService.disableFilmScreening(screening);
        return ResponseEntity.ok()
                .build();
    }

    @PostMapping("/{screening}")
    public ResponseEntity<?> editScreening(@PathVariable("screening") long screening,
                                           @RequestBody FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = screeningService.setFilmScreening(screening, screeningDto);
        return ResponseEntity.ok(filmScreening);
    }




}
