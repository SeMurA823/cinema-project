package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.FilmDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.events.CancelFilmEvent;
import com.muravyev.cinema.events.NotificationManager;
import com.muravyev.cinema.events.Observable;
import com.muravyev.cinema.repo.AgeLimitRepository;
import com.muravyev.cinema.repo.CountryRepository;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FilmService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FilmServiceImpl implements FilmService, Observable {
    private final FilmRepository filmRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final CountryRepository countryRepository;

    private NotificationManager notificationManager;

    public FilmServiceImpl(FilmRepository filmRepository,
                           AgeLimitRepository ageLimitRepository,
                           CountryRepository countryRepository) {
        this.filmRepository = filmRepository;
        this.ageLimitRepository = ageLimitRepository;
        this.countryRepository = countryRepository;
    }

    @Autowired
    @Override
    public void setNotificationManager(NotificationManager notificationManager) {
        this.notificationManager = notificationManager;
    }

    @Override
    public List<Film> getFilms(List<Long> id) {
        log.log(Level.DEBUG, "Getting film with id = {}", id);
        return filmRepository.findAllByIdInAndEntityStatus(id, EntityStatus.ACTIVE);
    }

    @Override
    public List<Film> getFilmsAnyStatus(List<Long> id) {
        return filmRepository.findAllById(id);
    }

    @Override
    public Page<Film> getPremieres(Pageable pageable) {
        log.log(Level.DEBUG, "Pageable (premieres): {}", pageable);
        return filmRepository.getComingPremieres(pageable);
    }

    @Override
    public Film addFilm(FilmDto filmDto) {
        Film film = merge(filmDto, new Film());
        return filmRepository.save(film);
    }

    private Film merge(FilmDto filmDto, Film film) {
        film.setName(filmDto.getName());
        film.setLocalPremiere(filmDto.getLocalPremiere());
        film.setWorldPremiere(filmDto.getWorldPremiere());
        film.setPlot(filmDto.getPlot());
        film.setDuration(filmDto.getDuration());
        AgeLimit ageLimit = ageLimitRepository.findById(filmDto.getAgeLimitId())
                .orElseThrow(EntityNotFoundException::new);
        film.setAgeLimit(ageLimit);
        List<Country> countryList = countryRepository.findAllById(filmDto.getCountriesId());
        film.setCountries(countryList);
        film.setEntityStatus((filmDto.isActive()) ? EntityStatus.ACTIVE : EntityStatus.NOT_ACTIVE);
        return film;
    }

    @Override
    public Page<Film> getArchiveFilms(Pageable pageable) {
        return filmRepository.getArchiveFilms(pageable);
    }

    @Override
    public List<Film> deleteFilms(List<Long> id) {
        List<Film> allById = filmRepository.findAllById(id);
        filmRepository.deleteAll(allById);
        return allById;
    }

    @Override
    public Film disableFilm(long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(EntityNotFoundException::new);
        film.setEntityStatus(EntityStatus.NOT_ACTIVE);
        Film savedFilm = filmRepository.save(film);
        notificationManager.notify(new CancelFilmEvent(savedFilm), CancelFilmEvent.class);
        return savedFilm;
    }

    @Override
    public Page<Film> getAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Film> setFilms(List<Long> id, FilmDto filmDto) {
        List<Film> allById = filmRepository.findAllById(id).stream()
                .map(x -> merge(filmDto, x))
                .collect(Collectors.toList());
        List<Film> savedFilms = filmRepository.saveAll(allById);
        savedFilms.forEach(x->notificationManager.notify(new CancelFilmEvent(x), CancelFilmEvent.class));
        return savedFilms;
    }
}
