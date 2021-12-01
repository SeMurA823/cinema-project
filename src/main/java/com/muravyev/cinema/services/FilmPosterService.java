package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmPoster;
import org.springframework.web.multipart.MultipartFile;

public interface FilmPosterService {
    FilmPoster save(MultipartFile file, Long film);

    FilmPoster save(MultipartFile file, Film film);

    void delete(long posterId);
}
