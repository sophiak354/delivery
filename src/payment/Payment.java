package payment;

import order.Order;
import console.ConsoleStep;
import order.OrderAction;
import order.OrderStatus;
import role.Customer;
import util.Notifiable;

import java.util.Scanner;

public class Payment implements ConsoleStep, OrderAction, Notifiable {
    private final Order order;
    private final Customer customer;
    private final Scanner scanner = new Scanner(System.in);

    public Payment(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
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
            System.out.println("Payment failed. Not enough money.");
            return;
        } else if (payment > totalPrice) {
            double tips = payment - totalPrice;
            System.out.println("Payment successful, thank you for tips: " + tips + ".");
        } else {
            System.out.println("Payment is successful.");
        }

        order.setStatus(OrderStatus.PAID);
        notifyUser("Order status is changed by " + customer.getName() + ". " + order);
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
