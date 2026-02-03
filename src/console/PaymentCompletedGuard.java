package console;

import delivery.DeliveryConfirmation;
import order.Order;
import order.OrderAction;
import order.OrderStatus;
import payment.Payment;

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

        while (!order.getStatus().equals(OrderStatus.PAID)) {

            System.out.println("Press 0 to retry payment, 1 to exit:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 0 -> {
                    payment.execute(order);
                }
                case 1 -> {
                    return false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        return true;
    }
}
