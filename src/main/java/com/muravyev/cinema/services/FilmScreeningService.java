package com.muravyev.cinema.services;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface FilmScreeningService {


    Page<FilmScreening> getAllFilmScreening(Pageable pageable);

    List<FilmScreening> getFilmScreenings(List<Long> id);

    void deleteFilmScreenings(List<Long> id);

    Collection<FilmScreening> getFilmScreenings(long filmId, Date start, Date end);

    Collection<FilmScreening> getFilmScreenings(long filmId, Date start);

    Collection<FilmScreening> getFilmScreenings(long filmId);

    void disableFilmScreening(long filmScreening);

    FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto);

    FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto);

    List<FilmScreeningSeat> getStatusSeats(long screeningId);

    List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening);
}
