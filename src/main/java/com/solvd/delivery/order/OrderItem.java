package com.solvd.delivery.order;

import com.solvd.delivery.offer.Offer;

public record OrderItem(Offer offer, int quantity) {
    public double totalPrice() {
        return offer.getPrice() * quantity;
    }
}
