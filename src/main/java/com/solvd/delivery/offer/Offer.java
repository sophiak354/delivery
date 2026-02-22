package com.solvd.delivery.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public abstract class Offer {
    private final String name;
    private final double price;

    public abstract OfferType getType();

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
