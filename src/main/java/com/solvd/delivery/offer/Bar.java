package com.solvd.delivery.offer;

public class Bar extends Offer {

    public Bar(String name, double price) {
        super(name, price);
    }

    @Override
    public OfferType getType() {
        return OfferType.BAR;
    }
}
