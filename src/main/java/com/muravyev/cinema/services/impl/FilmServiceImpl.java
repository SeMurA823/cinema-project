package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.AddingFilmDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.repo.AgeLimitRepository;
import com.muravyev.cinema.repo.CountryRepository;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FilmService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FilmServiceImpl implements FilmService {
    private FilmRepository filmRepository;
    private AgeLimitRepository ageLimitRepository;
    private CountryRepository countryRepository;

    public FilmServiceImpl(FilmRepository filmRepository,
                           AgeLimitRepository ageLimitRepository,
                           CountryRepository countryRepository) {
        this.filmRepository = filmRepository;
        this.ageLimitRepository = ageLimitRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Film getFilm(Long id) {
        log.log(Level.DEBUG, "Getting film with id = {}", id);
        return filmRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Page<Film> getPremieres(Pageable pageable) {
        log.log(Level.DEBUG, "Pageable (premieres): {}", pageable);
        return filmRepository.getComingPremieres(pageable);
    }

    @Override
    public Film addFilm(AddingFilmDto addingFilmDto) {
        Film film = new Film();
        film.setName(addingFilmDto.getName());
        film.setLocalPremiere(addingFilmDto.getLocalPremiere());
        film.setWorldPremiere(addingFilmDto.getWorldPremiere());
        film.setPlot(addingFilmDto.getPlot());
        film.setInsertDate(new Date());
        AgeLimit ageLimit = ageLimitRepository.findById(addingFilmDto.getAgeLimit())
                .orElseThrow(EntityNotFoundException::new);
        log.log(Level.DEBUG, "Age limit found : {}", ageLimit.getId());
        film.setAgeLimit(ageLimit);
        List<Country> countryList = countryRepository.findAllById(addingFilmDto.getCountries());
        log.log(Level.DEBUG, "Countries found : {}", countryList.stream()
                .map(Country::getCode)
                .collect(Collectors.toList()));
        film.setCountries(countryList);
        return filmRepository.save(film);
    }

    @Override
    public Page<Film> getArchiveFilms(Pageable pageable) {
        return filmRepository.getArchiveFilms(pageable);
    }

    @Override
    public void deleteFilm(long filmId) {
        filmRepository.deleteById(filmId);
    }

    @Override
    public void disableFilm(long filmId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(EntityNotFoundException::new);
        film.setDeleteDate(new Date());
        film.setEntityStatus(EntityStatus.DISABLE);
        filmRepository.save(film);
    }

}
