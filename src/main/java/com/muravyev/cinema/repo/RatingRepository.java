package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.FilmRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<FilmRating, Long> {

}
