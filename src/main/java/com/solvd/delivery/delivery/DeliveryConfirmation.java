package com.solvd.delivery.delivery;

import com.solvd.delivery.console.ConsoleStep;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.order.OrderValidator;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.util.Notifiable;

import java.util.Scanner;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class DeliveryConfirmation extends OrderValidator implements ConsoleStep, Notifiable {
    private static final Logger logger = LogManager.getLogger(DeliveryConfirmation.class);
    private final Order order;
    private final Customer customer;
    private final Scanner scanner;

    @Override
    public void run() {

        if (!validateStatus(order, OrderStatus.PAID, "confirm delivery")) {
            return;
        }

        System.out.println("Order is ready for preparation.");
        System.out.println("Press 1 to confirm preparation, 0 to cancel:");

        int choice = scanner.nextInt();

        if (choice == 1) {
            order.setStatus(OrderStatus.DELIVERY_CONFIRMED);
            notifyUser("Order status is changed by " +
                    customer.getName() + ". " + order
            );
            order.addHistory("Status changed to: " + order.getStatus().status());
        } else if (choice == 0) {
            return;
        } else {
            logger.warn("Invalid input.");
            return;
        }
    }

    @Override
    public void notifyUser(String message) {
        logger.info(message);
    }
}
