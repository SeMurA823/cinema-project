package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.FilmPoster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmPosterRepository extends JpaRepository<FilmPoster, Long> {

}
