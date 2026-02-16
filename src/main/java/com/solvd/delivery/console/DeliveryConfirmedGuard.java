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

        while (true) {
            try {
                if (!order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) {
                    throw new DeliveryNotConfirmedException();
                }
                return true;

            } catch (DeliveryNotConfirmedException e) {
                System.out.println(e.getMessage());

                while (true) {
                    System.out.println("Press 0 to retry confirmation, 1 to exit:");

                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 0 -> {
                            confirmation.run();
                            break;
                        }
                        case 1 -> {
                            return false;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                    if (choice == 0) break;
                }
            }
        }
    }
}
