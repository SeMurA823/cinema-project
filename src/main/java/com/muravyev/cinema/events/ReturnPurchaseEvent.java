package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.payment.Purchase;

import java.util.Map;

public class ReturnPurchaseEvent extends UserEvent<Purchase> {

    private final Purchase purchase;

    public ReturnPurchaseEvent(Map<Long, String> messages, Purchase purchase) {
        super(messages);
        this.purchase = purchase;
    }

    @Override
    public Purchase getValue() {
        return purchase;
    }
}
