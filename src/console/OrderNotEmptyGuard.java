package console;

import order.Order;

import java.util.Scanner;

public class OrderNotEmptyGuard implements FlowGuard {
    private final Order order;
    private final ConsoleStep selectionStep;
    private final Scanner scanner = new Scanner(System.in);

    public OrderNotEmptyGuard(Order order, ConsoleStep selectionStep) {
        this.order = order;
        this.selectionStep = selectionStep;
    }

    @Override
    public boolean allowContinue() {

        while (order.calculateTotalPrice() == 0) {

            System.out.println("Press 0 to select order again, 1 to exit:");

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
        }
        return true;
    }
}
