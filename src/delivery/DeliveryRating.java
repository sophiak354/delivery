package delivery;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DeliveryRating {
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
                System.out.println("Invalid rating. Try again.");
                continue;
            }

            System.out.println("Thank you for your rating " + rating + " - " + text);
            return;
        }
    }
}
