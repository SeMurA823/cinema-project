package com.muravyev.cinema.services;

import com.muravyev.cinema.entities.payment.Purchase;
import com.muravyev.cinema.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.Collection;
import java.util.List;

public interface PurchaseService {
    Page<Purchase> getAllPurchases(@PageableDefault Pageable pageable);

    void cancelPurchases(List<Long> ids);

    Purchase buy(Collection<Long> reservationId, User user);
}
