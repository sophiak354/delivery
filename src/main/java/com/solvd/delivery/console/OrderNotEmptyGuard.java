package com.solvd.delivery.console;

import com.solvd.delivery.exception.EmptyOrderException;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.util.Notifiable;

import java.util.Scanner;

public class OrderNotEmptyGuard implements FlowGuard, Notifiable {
    private final Order order;
    private final ConsoleStep selectionStep;
    private final Scanner scanner;
    private final Customer customer;

    public OrderNotEmptyGuard(Order order, ConsoleStep selectionStep, Scanner scanner, Customer customer) {
        this.order = order;
        this.selectionStep = selectionStep;
        this.scanner = scanner;
        this.customer = customer;
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }

    @Override
    public boolean allowContinue() {

        if (order.calculateTotalPrice() == 0) {
            for (int i = 0; i < 5; i++) {
                System.out.println("Order selection failed.");
                System.out.printf("Press 0 to select order again, 1 to exit (you did %s attempts out of 5): ", i);

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0 -> {
                        selectionStep.run();
                    }
                    case 1 -> {
                        return false;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }

                if (order.calculateTotalPrice() > 0) {
                    order.setStatus(OrderStatus.CREATED);
                    notifyUser("Order status is changed by " + customer.getName() + ". " + order);
                    order.addHistory("Status changed to: " + order.getStatus().status());
                    return true;
                }
            }
            throw new EmptyOrderException();
        }

        order.setStatus(OrderStatus.CREATED);
        notifyUser("Order status is changed by " + customer.getName() + ". " + order);
        order.addHistory("Status changed to: " + order.getStatus().status());
        return true;
    }
}
