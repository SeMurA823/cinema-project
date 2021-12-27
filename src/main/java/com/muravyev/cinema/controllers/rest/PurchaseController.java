package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.users.User;
import com.muravyev.cinema.services.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAllPurchases(@PageableDefault Pageable pageable) {
        Page<Purchase> allPurchases = purchaseService.getAllPurchases(pageable);
        return ResponseEntity.ok(allPurchases);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPurchases(@RequestParam("id") List<Long> ids) {
        purchaseService.cancelPurchases(ids);
        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestParam("id") List<Long> ids, Authentication authentication) {
        purchaseService.buy(ids, (User) authentication.getPrincipal());
        return ResponseEntity.ok()
                .build();
    }
}
