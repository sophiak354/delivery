package com.solvd.delivery.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum OfferType {
    MENU("Menu"),
    BAR("Bar");

    private final String offerType;

    public String offerType() {
        return this.offerType;
    }
}