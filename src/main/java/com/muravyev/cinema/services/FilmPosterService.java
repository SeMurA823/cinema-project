package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.PosterDto;
import com.muravyev.cinema.entities.film.FilmPoster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmPosterService {

    FilmPoster createPoster(PosterDto posterDto);

    void delete(Iterable<Long> posterId);

    List<FilmPoster> getPosters(Iterable<Long> id);

    Page<FilmPoster> getPosters(long filmId, Pageable pageable);
}
