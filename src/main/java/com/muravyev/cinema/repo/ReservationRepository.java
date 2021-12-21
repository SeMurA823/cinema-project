package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.hall.Seat;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.screening.FilmScreening;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT (count(r) > 0) FROM Reservation r " +
            "WHERE r.entityStatus = 'ACTIVE' AND r.expiryDate > current_timestamp " +
            "AND r.seat = :seat AND r.filmScreening = :filmScreening")
    boolean existsByFilmScreeningAndPlaceAndExpiryDateBefore(@Param("seat") Seat seat,
                                                             @Param("filmScreening") FilmScreening filmScreening);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.entityStatus = 'ACTIVE' AND r.customer = :customer AND r.expiryDate > current_timestamp ")
    Page<Reservation> findAllActualByCustomer(@Param("customer") Customer customer,
                                              Pageable pageable);

    Page<Reservation> findAllByCustomer(Customer customer, Pageable pageable);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.entityStatus = 'ACTIVE' AND r.id = :id " +
            "AND r.customer = :customer AND r.expiryDate > current_timestamp")
    Optional<Reservation> findByIdAndCustomer(@Param("id") long id, @Param("customer") Customer customer);

    @Query("SELECT r FROM Reservation r INNER JOIN r.customer c " +
            "WHERE c.user = :user AND r.id = :id AND r.entityStatus = 'ACTIVE' AND r.expiryDate > current_timestamp")
    Optional<Reservation> findByIdAndCustomerUser(@Param("id") long id, @Param("user") User customerUser);
}
