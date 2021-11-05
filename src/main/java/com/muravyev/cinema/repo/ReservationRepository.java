package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.film.FilmScreening;
import com.muravyev.cinema.entities.hall.Place;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT (count(r) > 0) FROM Reservation r " +
            "WHERE r.entityStatus = 'ENABLE' AND r.expiryDate < :expiry " +
            "AND r.place = :place AND r.filmScreening = :filmScreening")
    boolean existsByFilmScreeningAndPlaceAndExpiryDateBefore(@Param("place") Place place,
                                                             @Param("filmScreening") FilmScreening filmScreening,
                                                             @Param("expiry") Date expiry);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.entityStatus = 'ENABLE' AND r.customer = :customer AND r.expiryDate < :expiry")
    Page<Reservation> findAllByCustomerDAndExpiryDate(@Param("customer")Customer customer,
                                                      @Param("expiry") Date expiryDate,
                                                      Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.entityStatus = 'ENABLE' AND r.id = :id AND r.customer = :customer")
    Optional<Reservation> findByIdAndCustomer(@Param("id") long id, @Param("customer") Customer customer);

    @Query("SELECT r FROM Reservation r INNER JOIN r.customer c " +
            "WHERE c.user = :user AND r.id = :id AND r.entityStatus = 'ENABLE'")
    Optional<Reservation> findByIdAndCustomerUser(@Param("id") long id, @Param("user") User customerUser);
}
