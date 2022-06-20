package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmMark;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.FilmRepository;
import com.muravyev.cinema.repo.RatingRepository;
import com.muravyev.cinema.services.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
    @Transactional
    public FilmMark setMark(int mark, long filmId, User user) {
        FilmMark filmMark = ratingRepository.findByFilmIdAndUserAndEntityStatus(filmId,
                        user,
                        EntityStatus.ACTIVE)
                .orElse(createSemiMark(filmId, user));
        filmMark.setMark(mark);
        return ratingRepository.save(filmMark);
    }

    private FilmMark createSemiMark(long filmId, User user) {
        Film film = filmRepository.findByIdAndEntityStatus(filmId, EntityStatus.ACTIVE)
                .filter(x -> x.getLocalPremiere().isBefore(LocalDate.now()))
                .orElseThrow(EntityNotFoundException::new);
        FilmMark filmMark = new FilmMark();
        filmMark.setFilm(film);
        filmMark.setUser(user);
        return filmMark;
    }

    @Override
    public void deleteMark(long filmId, User user) {
        ratingRepository.deleteAllByUserAndFilmId(user, filmId);
    }

    @Override
    public double getRating(long filmId) {
        return ratingRepository.getRatingFilm(filmId, EntityStatus.ACTIVE)
                .orElse(0.);
    }

    @Override
    public FilmMark getMark(long filmId, User user) {
        return ratingRepository.findByFilmIdAndUserAndEntityStatus(filmId, user, EntityStatus.ACTIVE)
                .orElse(EMPTY_MARK);
    }


    private final static FilmMark EMPTY_MARK = new FilmMark() {
        {
            super.setMark(0);
        }

        @Override
        public void setCreated(Date created) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setUpdated(Date updated) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setEntityStatus(EntityStatus entityStatus) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setUser(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setFilm(Film film) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setMark(int mark) {
            throw new UnsupportedOperationException();
        }
    };
}
