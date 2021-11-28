package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.Film;
import org.springframework.web.multipart.MultipartFile;

public interface FilmPosterService {
    String save(MultipartFile file, Long film);

    String save(MultipartFile file, Film film);
}
