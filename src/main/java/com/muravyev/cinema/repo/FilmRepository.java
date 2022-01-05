package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.AgeLimit;
import com.muravyev.cinema.entities.film.Country;
import com.muravyev.cinema.entities.film.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere > CURRENT_DATE AND f.entityStatus = 'ACTIVE' ")
    Page<Film> getComingPremieres(Pageable pageable);

    @Query("SELECT f FROM Film f " +
            "WHERE f.localPremiere < CURRENT_DATE AND f.entityStatus = 'ACTIVE'")
    Page<Film> getArchiveFilms(Pageable pageable);

    Optional<Film> findByIdAndEntityStatus(Long id, EntityStatus entityStatus);

    @Query("select count(f) from Country c left join c.films f where c = :country and (c.films is empty or f.entityStatus = 'ACTIVE')")
    int countByCountry(@Param("country") Country country);

    @Query("select count(f) from AgeLimit a left join a.films f where a = :limit and (a.films is empty or f.entityStatus = 'ACTIVE')")
    int countByAgeLimit(@Param("limit") AgeLimit ageLimit);
}
