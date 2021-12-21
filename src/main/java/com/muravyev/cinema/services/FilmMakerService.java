package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmMakerDto;
import com.muravyev.cinema.dto.FilmMakerPostDto;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmMakerService {
    FilmMaker addFilmMaker(FilmMakerDto filmMakerDto);

    FilmMakerPost setFilmMaker(FilmMakerPostDto makerPostDto);

    void disableFilmMaker(long filmMakerId);

    void disableFilmMakerPost(long filmId, long makerId);

    void removeFilmMaker(long filmMakerId);

    void deleteFilmMakerPost(long filmId, long makerId);

    FilmMaker getFilmMaker(long id);

    List<FilmMakerPost> getFilmMakerPosts(long filmId);

    Page<FilmMakerPost> getAllPosts(long filmMakerId, Pageable pageable);
}
