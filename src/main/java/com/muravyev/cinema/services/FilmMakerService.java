package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmMakerDto;
import com.muravyev.cinema.dto.FilmMakerPostDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmMaker;
import com.muravyev.cinema.entities.film.FilmMakerPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface FilmMakerService {
    FilmMaker addFilmMaker(FilmMakerDto filmMakerDto);

    FilmMakerPost setFilmMakerPost(FilmMakerPostDto makerPostDto);

    FilmMaker setFilmMaker(long id, FilmMakerDto makerDto);

    void disableFilmMaker(long filmMakerId);

    void disableFilmMakerPost(long filmId, long makerId);

    void deleteFilmMaker(long filmMakerId);

    void deleteFilmMakerPost(long filmId, long makerId, String post);

    FilmMaker getFilmMaker(long id);

    List<FilmMakerPost> getFilmMakerPosts(long filmId);

    Page<FilmMakerPost> getAllPosts(long filmMakerId, Pageable pageable);

    Map<String, List<FilmMaker>> getFilmMakers(long filmId);

    Page<FilmMaker> getAllFilmMakers(Pageable pageable);

    void setFilmMakersStatus(Collection<Long> ids, EntityStatus status);

    Page<FilmMaker> getFilmMakers(String search, Pageable pageable);
}
