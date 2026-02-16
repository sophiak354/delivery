package com.solvd.delivery.offer;

import java.util.Objects;

public abstract class Offer {
    private final String name;
    private final double price;

    public Offer(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getType();

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Offer offer = (Offer) obj;

        return Double.compare(offer.price, price) == 0
                && Objects.equals(name, offer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name, price);
    }
}
