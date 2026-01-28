package order;

import offer.Offer;

public class OrderItem {
    private final Offer offer;
    private final int quantity;

    public OrderItem(Offer offer, int quantity) {
        this.offer = offer;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return offer.getPrice() * quantity;
    }

    public String getName() {
        return offer.getName();
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return offer.getType();
    }
}
