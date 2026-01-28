package order;

import offer.Offer;
import console.ConsoleStep;
import role.Customer;
import util.Notifiable;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderSelection implements ConsoleStep, Notifiable {
    private final List<Offer> offers;
    private final Order order;
    private final Customer customer;
    private final Scanner scanner = new Scanner(System.in);

    public OrderSelection(List<Offer> offers, Order order, Customer customer) {
        this.offers = offers;
        this.order = order;
        this.customer = customer;
    }

    @Override
    public void run() {
        List<Offer> menuToShow = buildUniqueOffers();

        printMenu(menuToShow);

        while (true) {
            System.out.println("Enter the number of the item you want to add to order (0 to finish):");

            int choice = scanner.nextInt();

            if (choice == 0) break;
            if (choice < 1 || choice > menuToShow.size()) {
                System.out.println("Invalid choice. Try again.");
                continue;
            }

            Offer selectedOffer = menuToShow.get(choice - 1);

            System.out.println("Enter quantity for " + selectedOffer.getName() + ":");

            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
                continue;
            }

            order.addItem(selectedOffer, quantity);

            System.out.println(quantity + " x " + selectedOffer.getName() + " was added to your order.");
        }

        if (order.calculateTotalPrice() == 0) {
            System.out.println("Your order is empty.");
            return;
        }
        System.out.println("\nYour final order:");

        order.printOrder();

        order.setStatus(OrderStatus.CREATED);
        notifyUser("Order status is changed by " + customer.getName() + ". " + order);
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }

    private List<Offer> buildUniqueOffers() {
        List<Offer> uniqueOffers = new ArrayList<>();

        for (Offer offer : offers) {
            if (!uniqueOffers.contains(offer)) {
                uniqueOffers.add(offer);
            }
        }
        return uniqueOffers;
    }

    private void printMenu(List<Offer> menuToShow) {

        for (int i = 0; i < menuToShow.size(); i++) {
            Offer offer = menuToShow.get(i);
            System.out.println((i + 1) + ". " +
                    offer.getType() + ": " +
                    offer.getName() + " - " +
                    offer.getPrice()
            );
        }
    }
}
