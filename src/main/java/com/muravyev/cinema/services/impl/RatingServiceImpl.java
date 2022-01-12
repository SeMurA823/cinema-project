package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmMark;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.repo.RatingRepository;
import com.muravyev.cinema.services.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;

@Service
public class RatingServiceImpl implements RatingService {
    private final FilmRepository filmRepository;
    private final RatingRepository ratingRepository;

    public RatingServiceImpl(FilmRepository filmRepository, RatingRepository ratingRepository) {
        this.filmRepository = filmRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public FilmMark setMark(int mark, long filmId, User user) {
        FilmMark filmMark = ratingRepository.findByFilmIdAndUserAndEntityStatus(filmId, user, EntityStatus.ACTIVE)
                .orElse(new FilmMark());
        filmMark.setMark(mark);
        filmMark.setFilm(filmRepository.findByIdAndEntityStatus(filmId, EntityStatus.ACTIVE)
                        .filter(x->x.getLocalPremiere().before(new Date()))
                .orElseThrow(EntityNotFoundException::new));
        filmMark.setUser(user);
        return ratingRepository.save(filmMark);
    }

    @Override
    public double getRating(long filmId) {
        return ratingRepository.getRatingFilm(filmId, EntityStatus.ACTIVE).orElse(0.);
    }

    @Override
    public FilmMark getMark(long filmId, User user) {
        return ratingRepository.findByFilmIdAndUserAndEntityStatus(filmId, user, EntityStatus.ACTIVE)
                .orElseThrow(EntityNotFoundException::new);
    }
}
