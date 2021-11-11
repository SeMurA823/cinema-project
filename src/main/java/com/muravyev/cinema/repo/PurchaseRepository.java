package com.muravyev.cinema.repo;

import com.muravyev.cinema.entities.payment.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
