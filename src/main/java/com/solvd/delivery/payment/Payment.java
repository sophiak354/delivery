package com.solvd.delivery.payment;

import com.solvd.delivery.order.Order;
import com.solvd.delivery.console.ConsoleStep;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderStatus;
import com.solvd.delivery.order.OrderValidator;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.util.Notifiable;

import java.util.Scanner;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class Payment extends OrderValidator implements ConsoleStep, OrderAction, Notifiable {
    private static final Logger logger = LogManager.getLogger(Payment.class);
    private final Order order;
    private final Customer customer;
    private final Scanner scanner;

    @Override
    public void run() {

        if (!validateStatus(order, OrderStatus.CREATED, "start payment")) {
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
        logger.info(message);
    }
}
