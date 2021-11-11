package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t " +
            "WHERE t.entityStatus = 'ENABLE' AND t.customer = :customer")
    Page<Ticket> findAllByCustomer(@Param("customer") Customer customer, Pageable pageable);

    @Query("SELECT t FROM Ticket t " +
            "JOIN t.customer c " +
            "WHERE c.user = :user AND t.entityStatus = 'ENABLE'")
    Page<Ticket> findAllByCustomerUser(@Param("user") User customerUser, Pageable pageable);
}
