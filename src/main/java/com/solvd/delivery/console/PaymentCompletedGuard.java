package com.solvd.delivery.console;

import com.solvd.delivery.exception.PaymentNotCompletedException;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderStatus;

import java.util.Scanner;

public class PaymentCompletedGuard implements FlowGuard {
    private final Order order;
    private final OrderAction payment;
    private final Scanner scanner;

    public PaymentCompletedGuard(Order order, OrderAction payment, Scanner scanner) {
        this.order = order;
        this.payment = payment;
        this.scanner = scanner;
    }

    @Override
    public boolean allowContinue() {

        while (true) {
            try {
                if (!order.getStatus().equals(OrderStatus.PAID)) {
                    throw new PaymentNotCompletedException();
                }

                return true;

            } catch (PaymentNotCompletedException e) {
                System.out.println(e.getMessage());

                while (true) {
                    System.out.println("Press 0 to retry payment, 1 to exit:");

                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 0 -> {
                            payment.execute(order);
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
