package com.solvd.delivery.order;

public abstract class OrderValidator {

    protected boolean validateStatus(Order order, String requiredStatus, String actionName) {

        if (!order.getStatus().equals(requiredStatus)) {
            System.out.println("Cannot " + actionName +
                    ": order status is " + order.getStatus());
            return false;
        }
        return true;
    }
}
