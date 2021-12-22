package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.payment.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findAllByPurchaseId(Long purchaseId, Pageable pageable);
}
