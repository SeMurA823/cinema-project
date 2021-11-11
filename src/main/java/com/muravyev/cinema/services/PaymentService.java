package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Purchase buyTicket(Reservation reservation, User user);

    Purchase buyTicket(long reservationId, User user);

    Page<Ticket> getTickets(Customer customer, Pageable pageable);

    Page<Ticket> getTickets(User user, Pageable pageable);
}
