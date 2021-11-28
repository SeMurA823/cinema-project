package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.services.FilmPosterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/posters")
public class FilmPosterRestController {
    private FilmPosterService posterService;

    public FilmPosterRestController(FilmPosterService posterService) {
        this.posterService = posterService;
    }


}
