package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentRestController {
    private PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<?> ticket(@RequestBody Map<String, String> request,
                                    Authentication authentication) {
        String bookingID = request.get("id");
        Purchase purchase = paymentService.buyTicket(Long.parseLong(bookingID), (User) authentication.getPrincipal());
        return ResponseEntity.ok(purchase);
    }
}
