package com.solvd.delivery;

import com.solvd.delivery.console.*;
import com.solvd.delivery.delivery.*;
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

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

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

        Customer customer = new Customer(customerName, customerAddress);
        Restaurant restaurant = new Restaurant("Bon appetit");
        Courier courier = new Courier("Alex");
        Order order = new Order();

        System.out.println("Hi, " + customerName + "! Today's menu:");

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

        System.out.println("Final order status is: " + order.getStatus());

        DeliveryRating rating = new DeliveryRating(scanner);
        rating.askAndPrint();

        order.printHistory();
    }
}
