package com.muravyev.cinema.controllers.rest;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.services.PurchaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getAllPurchases(@PageableDefault Pageable pageable) {
        Page<Purchase> allPurchases = purchaseService.getAllPurchases(pageable);
        return ResponseEntity.ok(allPurchases);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelPurchases(@RequestParam("id") Iterable<Long> ids) {
        purchaseService.cancelPurchases(ids);
        return ResponseEntity.ok()
                .build();
    }
}
