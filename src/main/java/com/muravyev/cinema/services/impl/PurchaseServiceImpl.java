package com.muravyev.cinema.services.impl;

import com.muravyev.cinema.entities.EntityStatus;
import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.repo.PurchaseRepository;
import com.muravyev.cinema.services.PurchaseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public Page<Purchase> getAllPurchases(@PageableDefault Pageable pageable) {
        return purchaseRepository.findAll(pageable);
    }

    @Override
    public List<Purchase> cancelPurchases(Iterable<Long> ids) {
        List<Purchase> purchases = purchaseRepository.findAllById(ids);
        purchases.forEach(this::cancelPurchase);
        return purchaseRepository.saveAll(purchases);
    }

    private Purchase cancelPurchase(Purchase purchase) {
        purchase.setEntityStatus(EntityStatus.ACTIVE);
        return purchase;
    }
}
