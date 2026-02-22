package com.solvd.delivery.order;

import com.solvd.delivery.role.Restaurant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class OrderPreparation extends OrderValidator implements OrderAction {
    protected final Restaurant restaurant;
    protected final Order order;

    public abstract void prepare(Order order);

    @Override
    public void execute(Order order) {
        prepare(order);
    }
}
