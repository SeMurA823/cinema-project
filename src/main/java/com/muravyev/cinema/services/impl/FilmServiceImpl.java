package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.FilmDto;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final CountryRepository countryRepository;

    public FilmServiceImpl(FilmRepository filmRepository,
                           AgeLimitRepository ageLimitRepository,
                           CountryRepository countryRepository) {
        this.filmRepository = filmRepository;
        this.ageLimitRepository = ageLimitRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Film> getFilms(List<Long> id) {
        log.log(Level.DEBUG, "Getting film with id = {}", id);
        return filmRepository.findAllById(id);
    }

    @Override
    public Page<Film> getPremieres(Pageable pageable) {
        log.log(Level.DEBUG, "Pageable (premieres): {}", pageable);
        return filmRepository.getComingPremieres(pageable);
    }

    @Override
    public Film addFilm(FilmDto filmDto) {
        Film film = from(filmDto, new Film());
        return filmRepository.save(film);
    }

    private Film from(FilmDto filmDto, Film film) {
        film.setName(filmDto.getName());
        film.setLocalPremiere(filmDto.getLocalPremiere());
        film.setWorldPremiere(filmDto.getWorldPremiere());
        film.setPlot(filmDto.getPlot());
        AgeLimit ageLimit = ageLimitRepository.findById(filmDto.getAgeLimitId())
                .orElseThrow(EntityNotFoundException::new);
        log.log(Level.DEBUG, "Age limit found : {}", ageLimit.getId());
        film.setAgeLimit(ageLimit);
        List<Country> countryList = countryRepository.findAllById(filmDto.getCountriesId());
        log.log(Level.DEBUG, "Countries found : {}", countryList.stream()
                .map(Country::getId)
                .collect(Collectors.toList()));
        film.setCountries(countryList);
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
        return filmRepository.save(film);
    }

    @Override
    public Page<Film> getAllFilms(Pageable pageable) {
        return filmRepository.findAll(pageable);
    }

    @Override
    public List<Film> setFilms(List<Long> id, FilmDto filmDto) {
        List<Film> allById = filmRepository.findAllById(id).stream()
                .map(x -> from(filmDto, x))
                .collect(Collectors.toList());
        return filmRepository.saveAll(allById);
    }

    private FilmDto from(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setCountriesId(film.getCountries().stream()
                .map(Country::getId)
                .collect(Collectors.toList()));
        filmDto.setName(film.getName());
        filmDto.setLocalPremiere(film.getLocalPremiere());
        filmDto.setWorldPremiere(film.getWorldPremiere());
        filmDto.setPlot(film.getPlot());
        filmDto.setAgeLimitId(film.getAgeLimit().getId());
        filmDto.setActive(film.isActive());
        return filmDto;
    }

    @Override
    public List<FilmDto> getFilmsDto(List<Long> id) {
        return this.getFilms(id).stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    @Override
    public Page<FilmDto> getPremieresDto(Pageable pageable) {
        return this.getPremieres(pageable).map(this::from);
    }

    @Override
    public FilmDto addFilmDto(FilmDto addingFilmDto) {
        return from(addFilm(addingFilmDto));
    }

    @Override
    public Page<FilmDto> getArchiveFilmsDto(Pageable pageable) {
        return getArchiveFilms(pageable).map(this::from);
    }

    @Override
    public List<FilmDto> deleteFilmsDto(List<Long> id) {
        return deleteFilms(id).stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDto disableFilmDto(long filmId) {
        return from(disableFilm(filmId));
    }

    @Override
    public Page<FilmDto> getAllFilmsDto(Pageable pageable) {
        return getAllFilms(pageable).map(this::from);
    }

    @Override
    public List<FilmDto> setFilmsDto(List<Long> filmId, FilmDto filmDto) {
        return setFilms(filmId, filmDto).stream()
                .map(this::from)
                .collect(Collectors.toList());
    }

}
