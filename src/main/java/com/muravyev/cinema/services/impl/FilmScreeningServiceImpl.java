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
import org.springframework.transaction.annotation.Transactional;

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
    public Page<FilmScreening> getAllFilmScreening(long filmId, Pageable pageable) {
        return screeningRepository.findAllByFilmId(filmId, pageable);
    }

    @Override
    public List<FilmScreening> getFilmScreenings(List<Long> id) {
        return screeningRepository.findAllById(id);
    }

    @Override
    public void deleteFilmScreenings(List<Long> id) {
        screeningRepository.deleteAllById(id);
    }

    @Override
    public Collection<FilmScreening> getFilmScreenings(long filmId, Date start, Date end) {
        return screeningRepository.getFilmScreenings(filmId, start, end);
    }

    @Override
    public Collection<FilmScreening> getFilmScreenings(long filmId, Date start) {
        return getFilmScreenings(filmId, start, toNextDay(start));
    }

    @Override
    public Collection<FilmScreening> getFilmScreenings(long filmId) {
        Date start = new Date();
        return getFilmScreenings(filmId, start);
    }

    @Override
    public FilmScreening setFilmScreening(long screeningId, FilmScreeningDto screeningDto) {
        FilmScreening filmScreening = merge(screeningDto,
                screeningRepository.findAllByIdAndDateAfter(screeningId, new Date())
                        .orElseThrow(EntityNotFoundException::new));
        return screeningRepository.save(filmScreening);
    }

    private FilmScreening merge(FilmScreeningDto screeningDto, FilmScreening filmScreening) {
        Film film = filmRepository.findById(screeningDto.getFilmId())
                .orElseThrow(EntityNotFoundException::new);
        Hall hall = hallRepository.findById(screeningDto.getHallId())
                .orElseThrow(EntityNotFoundException::new);
        filmScreening.setFilm(film);
        filmScreening.setHall(hall);
        filmScreening.setDate(screeningDto.getDate());
        filmScreening.setPrice(screeningDto.getPrice());
        filmScreening.setEntityStatus(screeningDto.isActive() ? EntityStatus.ACTIVE : EntityStatus.NOT_ACTIVE);
        return filmScreening;
    }

    @Override
    @Transactional
    public FilmScreening addFilmScreening(FilmScreeningDto filmScreeningDto) {
        FilmScreening filmScreening = merge(filmScreeningDto, new FilmScreening());
        return screeningRepository.save(filmScreening);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(long screeningId) {
        return screeningSeatRepository.findAllByScreeningId(screeningId);
    }

    @Override
    public List<FilmScreeningSeat> getStatusSeats(FilmScreening filmScreening) {
        return screeningSeatRepository.findAllByScreening(filmScreening);
    }

    @Override
    public FilmScreening getFilmScreening(long id) {
        return screeningRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void setStatusScreenings(Iterable<Long> ids, EntityStatus status) {
        List<FilmScreening> screenings = screeningRepository.findAllById(ids);
        screenings.forEach(x -> x.setEntityStatus(status));
        screeningRepository.saveAll(screenings);
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
