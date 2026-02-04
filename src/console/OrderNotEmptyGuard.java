package console;

import exception.EmptyOrderException;
import exception.InvalidMenuSelectionException;
import exception.InvalidQuantityException;
import order.Order;
import order.OrderStatus;
import role.Customer;
import util.Notifiable;

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

        while (true) {
            try {

                if (order.calculateTotalPrice() == 0) {
                    throw new EmptyOrderException();
                }

                order.setStatus(OrderStatus.CREATED);
                notifyUser("Order status is changed by " + customer.getName() + ". " + order);

                return true;

            } /*catch (InvalidMenuSelectionException | InvalidQuantityException e) {
                System.out.println(e.getMessage());

            }*/ catch (EmptyOrderException e) {
                System.out.println(e.getMessage());

                while (true) {
                    System.out.println("Press 0 to select order again, 1 to exit:");

                    int choice = scanner.nextInt();
                    switch (choice) {
                        case 0 -> {
                            selectionStep.run();
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
