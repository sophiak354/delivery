package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Courier;
import com.solvd.delivery.role.Customer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryFinish extends DeliveryStep {
    private static final Logger logger = LogManager.getLogger(DeliveryFinish.class);
    private final Customer customer;

    public DeliveryFinish(Courier courier, Order order, Customer customer) {
        super(courier, order);
        this.customer = customer;
    }

    @Override
    public void doStep(Order order) {

        if (!validateStatus(order, OrderStatus.ON_THE_WAY, "finish delivering")) {
            return;
        }

        order.setStatus(OrderStatus.DELIVERED);
        System.out.println("The order is delivered to: " +
                customer.getAddress() + " for " +
                customer.getName() + "."
        );
        notifyUser("Order status is changed by " +
                courier.getName() + ". " + order);
        order.addHistory("Status changed to: " + order.getStatus().status());
    }

    @Override
    public void notifyUser(String message) {
        logger.info(message);
    }
}
