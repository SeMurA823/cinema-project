package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;

import java.util.List;

public interface FilmScreeningSeatRepository extends ReadOnlyRepository<FilmScreeningSeat, Long> {
    List<FilmScreeningSeat> findAllByScreeningId(long screeningId);

    List<FilmScreeningSeat> findAllByScreening(FilmScreening screening);
}
