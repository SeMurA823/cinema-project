package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import com.muravyev.cinema.entities.hall.Hall;
import com.muravyev.cinema.entities.screening.FilmScreening;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface FilmScreeningRepository extends JpaRepository<FilmScreening, Long> {
    @Query("SELECT f FROM FilmScreening f " +
            "WHERE f.entityStatus = 'ACTIVE' " +
            "AND f.film.id = :filmId " +
            "AND f.date > current_timestamp " +
            "AND f.date BETWEEN :startDate AND :endDate " +
            "ORDER BY f.date ASC")
    List<FilmScreening> getActiveFilmScreeningsInDayInterval(@Param("filmId") long filmId,
                                                             @Param("startDate") Date startDate,
                                                             @Param("endDate") Date endDate);

    Page<FilmScreening> findAllByFilmId(Long filmId, Pageable pageable);

    Optional<FilmScreening> findByIdAndDateAfter(Long id, Date date);

    @Query("SELECT f FROM Film f JOIN FilmScreening fs ON fs.film = f " +
            "WHERE (fs.date BETWEEN :start AND :end) AND fs.entityStatus = :status AND f.entityStatus = :status " +
            "GROUP BY f")
    Page<Film> findAllFilmsBetweenDates(@Param("start") Date start,
                                        @Param("end") Date end,
                                        @Param("status")EntityStatus status,
                                        Pageable pageable);

    List<FilmScreening> findAllByFilmIdAndDateBetween(Long filmId, Date start, Date end);

    List<FilmScreening> findAllByIdInAndEntityStatusAndDateAfter(Collection<Long> id, EntityStatus entityStatus, Date date);

    Optional<FilmScreening> findByIdAndEntityStatus(long id, EntityStatus entityStatus);

    Stream<FilmScreening> streamAllByFilmAndDateAfterAndEntityStatus(Film film, Date date, EntityStatus entityStatus);

    Stream<FilmScreening> streamAllByHallAndDateAfterAndEntityStatus(Hall hall, Date date, EntityStatus entityStatus);


    List<FilmScreening> findAllByDateBetweenAndEntityStatusAndHallIdOrderByDate(Date date,
                                                                                Date date2,
                                                                                EntityStatus entityStatus,
                                                                                Long hallId);

}
