package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByFilmScreeningIdAndUserAndEntityStatusAndExpiryDateAfterOrderByCreatedDesc(Long filmScreening_id,
                                                                                                         User user,
                                                                                                         EntityStatus entityStatus,
                                                                                                         Date expiryDate);

    List<Reservation> findAllByIdInAndUserAndEntityStatus(Collection<Long> id, User user, EntityStatus entityStatus);

    @Query("select sc from Reservation r join FilmScreening sc on sc = r.filmScreening " +
            "where r.expiryDate > current_timestamp and r.user = :user and sc.film.id = :film and r.entityStatus = :status " +
            "group by sc order by sc.date asc")
    List<FilmScreening> findAllByUserAndFilmScreeningFilmIdAndEntityStatus(@Param("user") User user,
                                                                           @Param("film") Long filmId,
                                                                           @Param("status") EntityStatus entityStatus);
}
