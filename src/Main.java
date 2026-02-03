import console.*;
import delivery.*;
import offer.Bar;
import offer.Menu;
import offer.Offer;
import order.Order;
import order.OrderAction;
import order.OrderSelection;
import payment.Payment;
import role.Courier;
import role.Customer;
import role.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<Offer> offers = new ArrayList<>();
        offers.add(new Menu("Hamburger", 150.75));
        offers.add(new Menu("Chicken nuggets", 120.50));
        offers.add(new Menu("Salad", 80.75));
        offers.add(new Bar("Juice", 50.99));
        offers.add(new Bar("Coca-Cola", 80.00));
        offers.add(new Bar("Juice", 50.99));

        System.out.println("Enter your name:");
        String customerName = scanner.nextLine();

        System.out.println("Enter your address:");
        String customerAddress = scanner.nextLine();

        Customer customer = new Customer(customerName, customerAddress);
        Restaurant restaurant = new Restaurant("Bon appetit");
        Courier courier = new Courier("Alex");
        Order order = new Order();

        System.out.println("Hi, " + customerName + "! Today's menu:");

        ConsoleStep orderSelection = new OrderSelection(offers, order, customer, scanner);
        FlowGuard guardOrderNotEmpty = new OrderNotEmptyGuard(order, orderSelection, scanner);

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
        System.out.println("Please rate your delivery experience (1-5):");

        int rating = scanner.nextInt();

        System.out.println("Thank you for your rating: " + rating);
    }
}
