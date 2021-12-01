package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findAllByCustomerUserAndEntityStatusAndExpired(User customerUser,
                                                                EntityStatus entityStatus,
                                                                boolean expired,
                                                                Pageable pageable);

    Page<Ticket> findAllByCustomerUserAndEntityStatus(User customerUser, EntityStatus entityStatus, Pageable pageable);

    Optional<Ticket> findByIdAndCustomerUserAndEntityStatus(Long id, User customerUser, EntityStatus entityStatus);
}
