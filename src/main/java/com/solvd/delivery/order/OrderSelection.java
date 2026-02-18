package com.solvd.delivery.order;

import com.solvd.delivery.exception.InvalidMenuSelectionException;
import com.solvd.delivery.exception.InvalidQuantityException;
import com.solvd.delivery.offer.MenuCatalog;
import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.console.ConsoleStep;

import java.util.*;

public class OrderSelection implements ConsoleStep {
    private final MenuCatalog<Offer> catalog;
    private final Order order;
    private final Scanner scanner;

    public OrderSelection(MenuCatalog<Offer> catalog, Order order, Scanner scanner) {
        this.catalog = catalog;
        this.order = order;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        List<Offer> menuToShow = buildUniqueOffers();

        printMenu(menuToShow);

        int invalidAttempts = 0;

        while (true) {
            System.out.println("Enter the number of the item you want to add to order (0 to finish):");

            int choice = scanner.nextInt();

            if (choice == 0) break;
            if (choice < 1 || choice > menuToShow.size()) {

                invalidAttempts++;

                if (invalidAttempts >= 5) {
                    throw new InvalidMenuSelectionException();
                }

                System.out.printf("Invalid menu option selected. Try again (you did %s attempts out of 5).\n",
                        invalidAttempts
                );
                continue;
            }

            invalidAttempts = 0;

            Offer selectedOffer = menuToShow.get(choice - 1);

            System.out.println("Enter quantity for " + selectedOffer.getName() + ":");

            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                throw new InvalidQuantityException();
            }

            order.addItem(selectedOffer, quantity);

            System.out.println(quantity + " x " + selectedOffer.getName() + " was added to your order.");
        }

        System.out.println("\nYour final order:");

        order.printOrder();
    }

    private List<Offer> buildUniqueOffers() {

        Set<Offer> uniqueOffers = new LinkedHashSet<>(catalog.getItems());

        return new ArrayList<>(uniqueOffers);
    }

    private void printMenu(List<Offer> menuToShow) {

        for (int i = 0; i < menuToShow.size(); i++) {
            Offer offer = menuToShow.get(i);
            System.out.println((i + 1) + ". " +
                    offer.getType().offerType() + ": " +
                    offer.getName() + " - " +
                    offer.getPrice()
            );
        }
    }
}
