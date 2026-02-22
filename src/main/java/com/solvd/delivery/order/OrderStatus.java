package com.solvd.delivery.order;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

    NOT_SET("Not Set"),

    CREATED("Created"),

    PAID("Paid"),

    DELIVERY_CONFIRMED("Delivery Confirmed"),

    COOKING("Cooking"),

    COOKED("Cooked"),

    PACKING("Packing"),

    PACKED("Packed"),

    PICKED_UP("Picked up"),

    ON_THE_WAY("On the way"),

    DELIVERED("Delivered");

    private final String status;

    public String status() {
        return this.status;
    }
}