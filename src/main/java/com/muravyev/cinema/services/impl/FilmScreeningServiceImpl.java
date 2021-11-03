package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.AddingFilmScreeningDto;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmScreening;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.repo.HallRepository;
import com.muravyev.cinema.services.FilmScreeningService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Log4j2
public class FilmScreeningServiceImpl implements FilmScreeningService {
    private FilmScreeningRepository screeningRepository;
    private FilmRepository filmRepository;
    private HallRepository hallRepository;

    public FilmScreeningServiceImpl(FilmScreeningRepository screeningRepository,
                                    FilmRepository filmRepository,
                                    HallRepository hallRepository) {
        this.screeningRepository = screeningRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Collection<FilmScreening> getFilmScreening(long filmId, Date start, Date end) {
        log.log(Level.DEBUG, "getting film screening for film: {}", filmId);
        return screeningRepository.getFilmScreenings(filmId, start, end);
    }

    @Override
    public Collection<FilmScreening> getFilmScreening(long filmId, Date start) {
        return getFilmScreening(filmId, start, toNextDay(start));
    }

    @Override
    public Collection<FilmScreening> getFilmScreening(long filmId) {
        Date start = new Date();
        return getFilmScreening(filmId, start);
    }

    @Override
    public FilmScreening addFilmScreening(AddingFilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = new FilmScreening();
        Film film = filmRepository.findById(filmScreeningDto.getFilmId())
                .orElseThrow(EntityNotFoundException::new);
        Hall hall = hallRepository.findById(filmScreeningDto.getHallId())
                .orElseThrow(EntityNotFoundException::new);
        filmScreening.setFilm(film);
        filmScreening.setHall(hall);
        filmScreening.setDate(filmScreeningDto.getDate());
        return screeningRepository.save(filmScreening);
    }

    private Date toNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }
}
