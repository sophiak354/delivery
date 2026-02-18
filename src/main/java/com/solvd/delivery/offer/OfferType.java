package com.solvd.delivery.offer;

public enum OfferType {
    MENU("Menu"),
    BAR("Bar");

    private final String offerType;

    OfferType(String offerType) {
        this.offerType = offerType;
    }

    public String offerType() {
        return this.offerType;
    }
}