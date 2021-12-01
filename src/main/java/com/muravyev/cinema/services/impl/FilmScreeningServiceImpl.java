package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.FilmScreeningDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.screening.FilmScreeningSeat;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.repo.FilmScreeningRepository;
import com.muravyev.cinema.repo.FilmScreeningSeatRepository;
import com.muravyev.cinema.repo.HallRepository;
import com.muravyev.cinema.services.FilmScreeningService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class FilmScreeningServiceImpl implements FilmScreeningService {
    private final FilmScreeningRepository screeningRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final FilmScreeningSeatRepository screeningSeatRepository;

    public FilmScreeningServiceImpl(FilmScreeningRepository screeningRepository,
                                    FilmRepository filmRepository,
                                    HallRepository hallRepository,
                                    FilmScreeningSeatRepository screeningSeatRepository) {
        this.screeningRepository = screeningRepository;
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.screeningSeatRepository = screeningSeatRepository;
    }

    @Override
    public Page<FilmScreening> getAllFilmScreening(Pageable pageable) {
        return screeningRepository.findAll(pageable);
    }

    @Override
    public Collection<FilmScreening> getFilmScreening(long filmId, Date start, Date end) {
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
    public void disableFilmScreening(long filmScreening) {
        screeningRepository.findById(filmScreening)
                .ifPresent(sc -> {
                    sc.setEntityStatus(EntityStatus.NOT_ACTIVE);
                    screeningRepository.save(sc);
                });
    }

    @Override
    public FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = screeningRepository.findById(screeningId)
                .orElse(new FilmScreening());
        return configureFilmScreening(screeningDto, filmScreening);
    }

    private FilmScreening configureFilmScreening(FilmScreeningDto screeningDto, FilmScreening filmScreening) {
        Film film = filmRepository.findById(screeningDto.getFilmId())
                .orElseThrow(EntityNotFoundException::new);
        Hall hall = hallRepository.findById(screeningDto.getHallId())
                .orElseThrow(EntityNotFoundException::new);
        filmScreening.setFilm(film);
        filmScreening.setHall(hall);
        filmScreening.setDate(screeningDto.getDate());
        filmScreening.setPrice(screeningDto.getPrice());
        return screeningRepository.save(filmScreening);
    }

    @Override
    public FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = new FilmScreening();
        return configureFilmScreening(filmScreeningDto, filmScreening);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(long screeningId) {
        return screeningSeatRepository.findAllByScreeningId(screeningId);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening) {
        return screeningSeatRepository.findAllByScreening(filmScreening);
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
