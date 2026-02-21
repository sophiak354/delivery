package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Courier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryPickup extends DeliveryStep {
    private static final Logger logger = LogManager.getLogger(DeliveryPickup.class);

    public DeliveryPickup(Courier courier, Order order) {
        super(courier, order);
    }

    @Override
    public void doStep(Order order) {

        if (!validateStatus(order, OrderStatus.PACKED, "pick up order")) {
            return;
        }

        order.setStatus(OrderStatus.PICKED_UP);
        notifyUser("Order status is changed by " +
                courier.getName() + ". " + order);
        order.addHistory("Status changed to: " + order.getStatus().status());
    }

    @Override
    public void notifyUser(String message) {
        logger.info(message);
    }
}
