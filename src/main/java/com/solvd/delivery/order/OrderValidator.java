package com.solvd.delivery.order;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class OrderValidator {
    private static final Logger logger = LogManager.getLogger(OrderValidator.class);

    protected boolean validateStatus(Order order, OrderStatus requiredStatus, String actionName) {

        if (!order.getStatus().equals(requiredStatus)) {
            logger.warn("Cannot {}: order status is {}", actionName, order.getStatus().status());
            return false;
        }
        return true;
    }
}
