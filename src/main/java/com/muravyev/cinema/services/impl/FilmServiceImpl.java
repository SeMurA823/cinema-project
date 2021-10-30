package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.services.FilmService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {
    private FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Optional<Film> getFilm(Long id) {
        return filmRepository.findById(id);
    }

    @Override
    public Page<Film> getPremieres(Pageable pageable) {
        return filmRepository.comingPremieres(pageable);
    }
}
