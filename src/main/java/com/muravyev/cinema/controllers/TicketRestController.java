package com.muravyev.cinema.controllers;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.payment.Ticket;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketRestController {
    private TicketService ticketService;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> ticket(@RequestParam("reserve") long reserveId,
                                    Authentication authentication) {
        Purchase purchase = ticketService.buyTicket(reserveId, (User) authentication.getPrincipal());
        return ResponseEntity.ok(purchase);
    }

    @PostMapping("{ticket}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable("ticket") long ticketId, Authentication authentication) {
        Ticket ticket = ticketService.cancelTicket(ticketId, (User) authentication.getPrincipal());
        return ResponseEntity.ok(ticket);
    }

    @GetMapping(params = {"actual"})
    public ResponseEntity<?> actualTickets(@PageableDefault Pageable pageable, Authentication authentication) {
        Page<Ticket> actualTickets = ticketService.getActualTickets((User) authentication.getPrincipal(), pageable);
        return ResponseEntity.ok(actualTickets);
    }

    @GetMapping(params = {"archive"})
    public ResponseEntity<?> archiveTickets(@PageableDefault Pageable pageable, Authentication authentication) {
        Page<Ticket> archiveTicket = ticketService.getArchiveTickets((User) authentication.getPrincipal(), pageable);
        return ResponseEntity.ok(archiveTicket);
    }


}
