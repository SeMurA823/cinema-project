package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmMark;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RatingRepository extends JpaRepository<FilmMark, Long> {
    @Query("select avg(fm.mark) from FilmMark fm " +
            "join fm.film f " +
            "where fm.entityStatus = 'ACTIVE' and f.entityStatus = :filmStatus and f.id = :film")
    Optional<Double> getRatingFilm(@Param("film") long filmId, @Param("filmStatus") EntityStatus filmStatus);

    Optional<FilmMark> findByFilmIdAndUserAndEntityStatus(Long filmId, User user, EntityStatus entityStatus);
}
