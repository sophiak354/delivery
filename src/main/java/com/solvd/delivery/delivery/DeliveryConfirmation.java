package com.solvd.delivery.delivery;

import com.solvd.delivery.console.ConsoleStep;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.util.Notifiable;

import java.util.Scanner;

public class DeliveryConfirmation implements ConsoleStep, Notifiable {
    private final Order order;
    private final Customer customer;
    private final Scanner scanner;

    public DeliveryConfirmation(Order order, Customer customer, Scanner scanner) {
        this.order = order;
        this.customer = customer;
        this.scanner = scanner;
    }

    @Override
    public void run() {

        if (order.getStatus() != OrderStatus.PAID) {
            System.out.println("Delivery cannot be confirmed. Order is not paid.");
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
            order.addHistory("Status changed to Confirmed.");
        } else if (choice == 0) {
            return;
        } else {
            System.out.println("Invalid input. Delivery is not confirmed.");
            return;
        }
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
