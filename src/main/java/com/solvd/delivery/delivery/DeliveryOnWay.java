package com.solvd.delivery.delivery;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Courier;
import com.solvd.delivery.util.Delayable;

public class DeliveryOnWay extends DeliveryStep implements Delayable {

    public DeliveryOnWay(Courier courier, Order order) {
        super(courier, order);
    }

    @Override
    public void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void doStep(Order order) {

        if (!validateStatus(order, OrderStatus.PICKED_UP, "start delivery")) {
            return;
        }

        order.setStatus(OrderStatus.ON_THE_WAY);
        notifyUser("Order status is changed by " +
                courier.getName() + ". " + order);
        order.addHistory("Status changed to: " + order.getStatus().status());

        delay();
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
