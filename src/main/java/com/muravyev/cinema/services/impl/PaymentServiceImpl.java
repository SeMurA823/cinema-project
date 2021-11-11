package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Reservation;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.Customer;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.repo.PurchaseRepository;
import com.muravyev.cinema.repo.ReservationRepository;
import com.muravyev.cinema.repo.TicketRepository;
import com.muravyev.cinema.services.CustomerService;
import com.muravyev.cinema.services.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    private TicketRepository ticketRepository;
    private PurchaseRepository purchaseRepository;
    private ReservationRepository reservationRepository;
    private CustomerService customerService;

    public PaymentServiceImpl(TicketRepository ticketRepository,
                              PurchaseRepository purchaseRepository,
                              ReservationRepository reservationRepository,
                              CustomerService customerService) {
        this.ticketRepository = ticketRepository;
        this.purchaseRepository = purchaseRepository;
        this.reservationRepository = reservationRepository;
        this.customerService = customerService;
    }

    @Override
    public Purchase buyTicket(Reservation reservation, User user) {
        if (!reservation.getCustomer().getUser().equals(user))
            throw new RuntimeException();
        Ticket ticket = new Ticket();
        ticket.setCustomer(reservation.getCustomer());
        ticket.setFilmScreening(reservation.getFilmScreening());
        ticket.setPlace(reservation.getPlace());
        Purchase purchase = new Purchase();
        purchase.setCustomer(reservation.getCustomer());
        purchase.setPrice(reservation.getFilmScreening().getPrice());
        purchase.setTicket(ticketRepository.save(ticket));
        purchaseRepository.save(purchase);
        return purchase;
    }

    @Override
    public Purchase buyTicket(long reservationId, User user) {
        Reservation reservation = reservationRepository.findByIdAndCustomer(reservationId, customerService.getCustomer(user)).orElseThrow(RuntimeException::new);
        return buyTicket(reservation, user);
    }

    @Override
    public Page<Ticket> getTickets(Customer customer, Pageable pageable) {
        return ticketRepository.findAllByCustomer(customer, pageable);
    }

    @Override
    public Page<Ticket> getTickets(User user, Pageable pageable) {
        return ticketRepository.findAllByCustomerUser(user, pageable);
    }


}
