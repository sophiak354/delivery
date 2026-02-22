package com.solvd.delivery.order;

import com.solvd.delivery.exception.InvalidMenuSelectionException;
import com.solvd.delivery.exception.InvalidQuantityException;
import com.solvd.delivery.offer.MenuCatalog;
import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.console.ConsoleStep;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RequiredArgsConstructor
public class OrderSelection implements ConsoleStep {
    private static final Logger logger = LogManager.getLogger(OrderSelection.class);
    private final MenuCatalog<Offer> catalog;
    private final Order order;
    private final Scanner scanner;

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
                    logger.error("Too many attempts.");
                    throw new InvalidMenuSelectionException();
                }
                logger.warn("Invalid menu option selected. Try again ({} attempts out of 5).\n",
                        invalidAttempts
                );
                continue;
            }

            invalidAttempts = 0;

            Offer selectedOffer = menuToShow.get(choice - 1);

            System.out.println("Enter quantity for " + selectedOffer.getName() + ":");

            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                logger.error("Quantity is less or equal to zero.");
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
