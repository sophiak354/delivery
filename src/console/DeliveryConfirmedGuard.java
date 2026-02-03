package console;

import delivery.DeliveryConfirmation;
import order.Order;
import order.OrderStatus;

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

        while (!order.getStatus().equals(OrderStatus.DELIVERY_CONFIRMED)) {

            System.out.println("Press 0 to retry confirmation, 1 to exit:");

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
        }
        return true;
    }
}
