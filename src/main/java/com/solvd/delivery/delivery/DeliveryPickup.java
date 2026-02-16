package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Courier;

public class DeliveryPickup extends DeliveryStep {

    public DeliveryPickup(Courier courier, Order order) {
        super(courier, order);
    }

    @Override
    public void doStep(Order order) {

        if (!validateStatus(order, "Packed", "pick up order")) {
            return;
        }

        order.setStatus(OrderStatus.PICKED_UP);
        notifyUser("Order status is changed by " +
                courier.getName() + ". " + order);
        order.addHistory("Status changed to Picked Up.");
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
