package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.screening.FilmScreening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FilmScreeningRepository extends JpaRepository<FilmScreening, Long> {
    @Query("SELECT f FROM FilmScreening f " +
            "WHERE f.entityStatus = 'ACTIVE' " +
            "AND f.film.id = :filmId " +
            "AND f.date >= :startDate " +
            "AND f.date < :endDate " +
            "ORDER BY f.date ASC")
    List<FilmScreening> getFilmScreenings(@Param("filmId") long filmId,
                                          @Param("startDate") Date startDate,
                                          @Param("endDate") Date endDate);

    Page<FilmScreening> findAllByFilmId(Long filmId, Pageable pageable);

    Optional<FilmScreening> findAllByIdAndDateAfter(Long id, Date date);

}
