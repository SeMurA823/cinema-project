package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FilmService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class FilmServiceImpl implements FilmService {
    private FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Optional<Film> getFilm(Long id) {
        log.log(Level.DEBUG, "Getting film with id = {}", id);
        return filmRepository.findById(id);
    }

    @Override
    public Page<Film> getPremieres(Pageable pageable) {
        log.log(Level.DEBUG, "Pageable (premieres): {}", pageable);
        return filmRepository.comingPremieres(pageable);
    }

}
