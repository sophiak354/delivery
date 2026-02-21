package com.solvd.delivery.console;

import com.solvd.delivery.exception.PaymentNotCompletedException;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderStatus;

import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentCompletedGuard implements FlowGuard {
    private static final Logger logger = LogManager.getLogger(PaymentCompletedGuard.class);
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

        if (!order.getStatus().equals(OrderStatus.PAID)) {
            for (int i = 0; i < 5; i++) {
                logger.warn("Payment failed.");
                System.out.printf("Press 0 to retry payment, 1 to exit (you did %s attempts out of 5): ", i);

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> {
                        payment.execute(order);
                    }
                    case 1 -> {
                        return false;
                    }
                    default -> logger.warn("Invalid choice. Try again.");
                }
                if (order.getStatus().equals(OrderStatus.PAID)) return true;
            }
            logger.error("Payment is not completed.");
            throw new PaymentNotCompletedException();
        }
        return true;
    }
}
