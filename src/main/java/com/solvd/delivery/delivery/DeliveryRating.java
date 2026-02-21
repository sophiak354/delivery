package com.solvd.delivery.delivery;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeliveryRating {
    private static final Logger logger = LogManager.getLogger(DeliveryRating.class);
    private final Scanner scanner;
    private final Map<Integer, String> labels = new HashMap<>();

    public DeliveryRating(Scanner scanner) {
        this.scanner = scanner;

        labels.put(1, "Bad");
        labels.put(2, "Not great");
        labels.put(3, "Okay");
        labels.put(4, "Good");
        labels.put(5, "Excellent");
    }

    public void askAndPrint() {
        while (true) {
            System.out.println("Rate the delivery (1-5):");

            int rating = scanner.nextInt();

            String text = labels.get(rating);

            if (text == null) {
                logger.warn("Invalid rating. Try again.");
                continue;
            }

            System.out.println("Thank you for your rating " + rating + " - " + text);
            return;
        }
    }
}
