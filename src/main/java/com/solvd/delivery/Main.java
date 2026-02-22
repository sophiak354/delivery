package com.solvd.delivery;

import com.solvd.delivery.console.*;
import com.solvd.delivery.delivery.*;
import com.solvd.delivery.fileutil.WordCounter;
import com.solvd.delivery.offer.Bar;
import com.solvd.delivery.offer.Menu;
import com.solvd.delivery.offer.MenuCatalog;
import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.order.Order;
import com.solvd.delivery.order.OrderAction;
import com.solvd.delivery.order.OrderSelection;
import com.solvd.delivery.payment.Payment;
import com.solvd.delivery.role.Courier;
import com.solvd.delivery.role.Customer;
import com.solvd.delivery.role.Restaurant;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Word Counter - Enter word #1: ");
        String wordOne = scanner.nextLine();
        System.out.print("Word Counter - Enter word #2: ");
        String wordTwo = scanner.nextLine();
        System.out.print("Word Counter - Enter word #3: ");
        String wordThree = scanner.nextLine();

        WordCounter.countWords(List.of(wordOne, wordTwo, wordThree));

        MenuCatalog<Offer> catalog = new MenuCatalog<>();
        catalog.addItem(new Menu("Hamburger", 150.75));
        catalog.addItem(new Menu("Chicken nuggets", 120.50));
        catalog.addItem(new Menu("Salad", 80.75));
        catalog.addItem(new Bar("Juice", 50.99));
        catalog.addItem(new Bar("Coca-Cola", 80.00));
        catalog.addItem(new Bar("Juice", 50.99));

        System.out.println("Enter your name:");
        String customerName = scanner.nextLine();

        System.out.println("Enter your address:");
        String customerAddress = scanner.nextLine();

        DeliveryFeeService feeService = new DeliveryFeeService();
        double distance = ThreadLocalRandom.current().nextDouble(0, 10);

        System.out.printf("Delivery distance: %.2f km%n", distance);

        System.out.println("Standard fee: "
                + feeService.calculateStandard(distance));
        System.out.println("Express fee: "
                + feeService.calculateExpress(distance));
        System.out.println("Promo fee: "
                + feeService.calculatePromo(distance));

        System.out.println("\nOnline fee payment is not available now. Please, pay by cash.");

        Customer customer = new Customer(customerName, customerAddress);
        Restaurant restaurant = new Restaurant("Bon appetit");
        Courier courier = new Courier("Alex");
        Order order = new Order();

        System.out.println("\nHi, " + customerName + "! Today's menu:");

        ConsoleStep orderSelection = new OrderSelection(catalog, order, scanner);
        FlowGuard guardOrderNotEmpty = new OrderNotEmptyGuard(order, orderSelection, scanner, customer);

        orderSelection.run();
        if (!guardOrderNotEmpty.allowContinue()) return;

        OrderAction payment = new Payment(order, customer, scanner);
        FlowGuard guardPaymentCompleted = new PaymentCompletedGuard(order, payment, scanner);

        payment.execute(order);
        if (!guardPaymentCompleted.allowContinue()) return;

        DeliveryConfirmation confirmation = new DeliveryConfirmation(order, customer, scanner);
        FlowGuard guardDeliveryConfirmed = new DeliveryConfirmedGuard(order, confirmation, scanner);

        confirmation.run();
        if (!guardDeliveryConfirmed.allowContinue()) return;

        scanner.nextLine();
        System.out.println("Press Enter to continue to cooking...");
        scanner.nextLine();

        OrderAction cooking = new Cooking(restaurant, order);
        cooking.execute(order);

        System.out.println("Press Enter to continue to packing...");
        scanner.nextLine();

        OrderAction packing = new Packing(restaurant, order);
        packing.execute(order);

        System.out.println("Press Enter to continue to delivery...");
        scanner.nextLine();

        OrderAction pickup = new DeliveryPickup(courier, order);
        pickup.execute(order);

        OrderAction onWay = new DeliveryOnWay(courier, order);
        onWay.execute(order);

        System.out.println("Press Enter to receive order...");
        scanner.nextLine();

        OrderAction finish = new DeliveryFinish(courier, order, customer);
        finish.execute(order);

        logger.info("Final order status is: {}", order.getStatus().status());

        DeliveryRating rating = new DeliveryRating(scanner);
        rating.askAndPrint();

        order.printHistory();
    }
}
