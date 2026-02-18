package com.solvd.delivery.payment;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.console.ConsoleStep;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.util.Notifiable;

import java.util.Scanner;

public class Payment implements ConsoleStep, OrderAction, Notifiable {
    private final Order order;
    private final Customer customer;
    private final Scanner scanner;

    public Payment(Order order, Customer customer, Scanner scanner) {
        this.order = order;
        this.customer = customer;
        this.scanner = scanner;
    }

    @Override
    public void run() {

        if (order.getStatus() != OrderStatus.CREATED) {
            System.out.println("Payment is not allowed. Order is not in Created status.");
            return;
        }

        double totalPrice = order.calculateTotalPrice();

        System.out.println("Total amount to pay: " + totalPrice);
        System.out.println("Enter payment amount:");

        double payment = scanner.nextDouble();

        if (payment < totalPrice) {
            return;
        } else if (payment > totalPrice) {
            double tips = payment - totalPrice;
            System.out.println("Payment successful, thank you for tips: " + tips + ".");
        } else {
            System.out.println("Payment is successful.");
        }

        order.setStatus(OrderStatus.PAID);
        notifyUser("Order status is changed by " + customer.getName() + ". " + order);
        order.addHistory("Status changed to: " + order.getStatus().status());
    }

    @Override
    public void execute(Order order) {
        run();
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
