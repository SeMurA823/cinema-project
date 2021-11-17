package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.film.FilmRating;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<FilmRating, Long> {
    @Query("SELECT avg(r.rating) FROM FilmRating r WHERE r.film = ?1")
    double getAverageRatingByFilmId(Long filmId);

    Optional<FilmRating> findByFilmIdAndCustomer(Long filmId, Customer customer);

    @NotNull
    @Query("SELECT r FROM FilmRating r " +
            "JOIN r.customer c JOIN c.user u " +
            "WHERE u = :user AND r.film.id = :filmId " +
            "AND u.entityStatus = :u_status AND c.entityStatus = :c_status")
    Optional<FilmRating> findByFilmIdAndUser(
            @Param("filmId") Long filmId,
            @Param("user") User user,
            @Param("u_status") EntityStatus userEntityStatus,
            @Param("c_status") EntityStatus customerEntityStatus);

    default Optional<FilmRating> findByFilmIdAndUser(Long filmId, User user) {
        return findByFilmIdAndUser(filmId, user, EntityStatus.ACTIVE, EntityStatus.ACTIVE);
    }
}
