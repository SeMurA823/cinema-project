package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere > CURRENT_DATE AND f.entityStatus = 'ACTIVE' ")
    Page<Film> getComingPremieres(Pageable pageable);

    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere < CURRENT_DATE AND f.entityStatus = 'ACTIVE'")
    Page<Film> getArchiveFilms(Pageable pageable);

    Optional<Film> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

    @Query("SELECT f FROM Film f JOIN FilmScreening fs ON fs.film = f " +
            "WHERE (fs.date BETWEEN :start AND :end) AND fs.entityStatus = :status " +
            "GROUP BY f")
    Page<Film> findAllBetweenDates(@Param("start") Date dateStart, @Param("end") Date dateEnd, @Param("status") EntityStatus status, Pageable pageable);
}
