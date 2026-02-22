package com.solvd.delivery.offer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MenuCatalog<T extends Offer> {

    private final List<T> items = new ArrayList<>();

    public void addItem(T item) {
        items.add(item);
    }
}
