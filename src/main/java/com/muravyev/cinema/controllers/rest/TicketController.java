package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.TicketService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page", "size", "purchase"})
    public ResponseEntity<?> getTickets(@RequestParam("purchase") long purchaseId, @PageableDefault Pageable pageable) {
        Page<Ticket> tickets = ticketService.getTickets(purchaseId, pageable);
        return ResponseEntity.ok(tickets);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/cancel", params = {"id"})
    public ResponseEntity<?> cancelTickets(@RequestParam("id") List<Long> ids) {
        ticketService.cancelTickets(ids);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/mytickets")
    public ResponseEntity<?> getTickets(@PageableDefault Pageable pageable, Authentication authentication) {
        Page<Ticket> tickets = ticketService.getTickets((User) authentication.getPrincipal(), pageable);
        return ResponseEntity.ok(tickets);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable("id") long id, Authentication authentication) {
        ticketService.cancelTicket((User) authentication.getPrincipal(), id);
        return ResponseEntity.ok()
                .build();
    }
}
