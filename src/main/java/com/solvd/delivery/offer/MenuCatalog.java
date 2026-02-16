package com.solvd.delivery.offer;

import java.util.ArrayList;
import java.util.List;

public class MenuCatalog<T extends Offer> {

    private final List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }

    public List<T> getItems() {
        return items;
    }
}
