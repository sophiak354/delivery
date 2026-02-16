package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderPreparation;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Restaurant;
import com.solvd.delivery.util.Delayable;
import com.solvd.delivery.util.Notifiable;

public class Packing extends OrderPreparation implements Delayable, Notifiable {

    public Packing(Restaurant restaurant, Order order) {
        super(restaurant, order);
    }

    @Override
    public void delay() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void prepare(Order order) {

        if (!validateStatus(order, OrderStatus.COOKED, "start packing")) {
            return;
        }

        order.setStatus(OrderStatus.PACKING);
        notifyUser("Order status is changed by " + restaurant.getName() + ". " + order);
        order.addHistory("Status changed to Packing.");

        System.out.println("Packing is in progress...");

        delay();

        order.setStatus(OrderStatus.PACKED);
        notifyUser("Order status is changed by " + restaurant.getName() + ". " + order);
        order.addHistory("Status changed to Packed.");
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
