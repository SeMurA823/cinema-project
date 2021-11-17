package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.dto.RateDto;
import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.film.FilmRating;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.RatingRepository;
import com.muravyev.cinema.services.CustomerService;
import com.muravyev.cinema.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final CustomerService customerService;

    public RatingServiceImpl(RatingRepository ratingRepository, CustomerService customerService) {
        this.ratingRepository = ratingRepository;
        this.customerService = customerService;
    }

    @Override
    public FilmRating setRating(RateDto rateDto, User user) {
        FilmRating filmRating = new FilmRating();
        filmRating.setRating(rateDto.getRating());
        filmRating.setCustomer(customerService.getCustomer(user));
        Film film = new Film();
        film.setId(rateDto.getFilmId());
        filmRating.setFilm(film);
        return ratingRepository.save(filmRating);
    }

    @Override
    public FilmRating getRating(long filmId, User user) {
        return ratingRepository.findByFilmIdAndUser(filmId,
                user,
                EntityStatus.ACTIVE,
                EntityStatus.ACTIVE).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public double getAverage(long filmId) {
        return ratingRepository.getAverageRatingByFilmId(filmId);
    }

    @Override
    @Transactional
    public void deleteRating(long filmId, User user) {
        FilmRating rating = ratingRepository.findByFilmIdAndUser(filmId, user)
                .orElseThrow(EntityNotFoundException::new);
        ratingRepository.delete(rating);
    }

    private Customer toCustomer(User user) {
        return customerService.getCustomer(user);
    }
}
