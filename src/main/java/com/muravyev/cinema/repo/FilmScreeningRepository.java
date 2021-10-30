package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.FilmScreening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FilmScreeningRepository extends JpaRepository<FilmScreening, Long> {
    @Query("SELECT f FROM FilmScreening f " +
            "WHERE f.entityStatus = 'ENABLE' " +
            "AND f.film.id = :filmId " +
            "AND f.date >= :startDate " +
            "AND f.date < :endDate " +
            "ORDER BY f.date ASC")
    List<FilmScreening> getFilmScreenings(@Param("filmId") long filmId,
                                          @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate);
}
