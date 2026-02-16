package com.solvd.delivery.offer;

public class Menu extends Offer {

    public Menu(String name, double price) {
        super(name, price);
    }

    @Override
    public String getType() {
        return OfferType.MENU;
    }
}
