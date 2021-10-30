package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.film.FilmScreening;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.services.FilmScreeningService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Log4j2
public class FilmScreeningServiceImpl implements FilmScreeningService {
    private FilmScreeningRepository screeningRepository;

    public FilmScreeningServiceImpl(FilmScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
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
