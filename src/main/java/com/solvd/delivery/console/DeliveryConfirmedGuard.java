package com.solvd.delivery.console;

import com.solvd.delivery.delivery.DeliveryConfirmation;
import com.solvd.delivery.exception.DeliveryNotConfirmedException;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;

import java.util.Scanner;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class DeliveryConfirmedGuard implements FlowGuard {
    private static final Logger logger = LogManager.getLogger(DeliveryConfirmedGuard.class);
    private final Order order;
    private final DeliveryConfirmation confirmation;
    private final Scanner scanner;

    @Override
    public boolean allowContinue() {

        if (!order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) {
            for (int i = 0; i < 5; i++) {
                logger.warn("Delivery confirmation failed.");
                System.out.printf("Press 0 to retry confirmation, 1 to exit (you did %s attempts out of 5): ", i);

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> {
                        confirmation.run();
                    }
                    case 1 -> {
                        return false;
                    }
                    default -> logger.warn("Invalid choice. Try again.");
                }
                if (order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) return true;
            }
            logger.error("Delivery is not confirmed.");
            throw new DeliveryNotConfirmedException();
        }
        return true;
    }
}
