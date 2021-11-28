package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.dto.AddingFilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.services.FilmScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/screenings")
public class FilmScreeningAdminRestController {
    private FilmScreeningService screeningService;

    @PostMapping("/add")
    public ResponseEntity<?> addScreening(@RequestBody AddingFilmScreeningDto addingFilmScreeningDto){
        FilmScreening filmScreening = screeningService.addFilmScreening(addingFilmScreeningDto);
        return ResponseEntity.ok(filmScreening);
    }

    public FilmScreeningAdminRestController(FilmScreeningService screeningService) {
        this.screeningService = screeningService;
    }
}
