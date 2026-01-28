package delivery;

import console.ConsoleStep;
import order.Order;
import order.OrderStatus;
import role.Customer;
import util.Notifiable;

import java.util.Scanner;

public class DeliveryConfirmation implements ConsoleStep, Notifiable {
    private final Order order;
    private final Customer customer;
    private final Scanner scanner = new Scanner(System.in);

    public DeliveryConfirmation(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
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
        } else if (choice == 0) {
            System.out.println("Confirmation is cancelled by " +
                    customer.getName()
            );
        } else {
            System.out.println("Invalid input. Delivery is not confirmed.");
        }
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
