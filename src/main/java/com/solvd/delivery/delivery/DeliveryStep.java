package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderValidator;
import com.solvd.delivery.role.Courier;
import com.solvd.delivery.util.Notifiable;

public abstract class DeliveryStep extends OrderValidator implements OrderAction, Notifiable {
    protected final Courier courier;
    protected final Order order;

    public DeliveryStep(Courier courier, Order order) {
        this.courier = courier;
        this.order = order;
    }

    public abstract void doStep(Order order);

    @Override
    public void execute(Order order) {
        doStep(order);
    }
}
