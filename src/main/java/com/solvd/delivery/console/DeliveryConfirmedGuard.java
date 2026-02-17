package com.solvd.delivery.console;

import com.solvd.delivery.delivery.DeliveryConfirmation;
import com.solvd.delivery.exception.DeliveryNotConfirmedException;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;

import java.util.Scanner;

public class DeliveryConfirmedGuard implements FlowGuard {
    private final Order order;
    private final DeliveryConfirmation confirmation;
    private final Scanner scanner;

    public DeliveryConfirmedGuard(Order order, DeliveryConfirmation confirmation, Scanner scanner) {
        this.order = order;
        this.confirmation = confirmation;
        this.scanner = scanner;
    }

    @Override
    public boolean allowContinue() {

        if (!order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Delivery confirmation failed.");
                System.out.printf("Press 0 to retry confirmation, 1 to exit (you did %s attempts out of 5): ", i);

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> {
                        confirmation.run();
                    }
                    case 1 -> {
                        return false;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
                if (order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) return true;
            }
            throw new DeliveryNotConfirmedException();
        }
        return true;
    }
}
