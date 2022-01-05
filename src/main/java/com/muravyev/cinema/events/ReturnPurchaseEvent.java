package com.muravyev.cinema.events;

import com.muravyev.cinema.entities.payment.Purchase;

public class ReturnPurchaseEvent implements Event<Purchase> {

    private final Purchase purchase;

    public ReturnPurchaseEvent(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public Purchase getValue() {
        return purchase;
    }
}
