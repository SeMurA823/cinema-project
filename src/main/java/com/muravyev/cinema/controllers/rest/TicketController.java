package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(params = {"page", "size", "purchase"})
    public ResponseEntity<?> getTickets(@RequestParam("purchase") long purchaseId, @PageableDefault Pageable pageable) {
        Page<Ticket> tickets = ticketService.getTickets(purchaseId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cancel")
    public ResponseEntity<?> setStatusTicket(@RequestParam("id") Iterable<Long> ids) {
        ticketService.cancelTickets(ids);
        return ResponseEntity.ok()
                .build();
    }
}
