package com.solvd.delivery.order;

import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.util.SimpleLinkedList;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String status;
    private final List<OrderItem> items;
    private final SimpleLinkedList<String> history = new SimpleLinkedList<>();

    public Order() {
        this.status = "Not Set";
        this.items = new ArrayList<>();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void addItem(Offer offer, int quantity) {
        OrderItem item = new OrderItem(offer, quantity);
        items.add(item);
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void printOrder() {

        for (OrderItem item : items) {
            System.out.println(item.getType() + ": " + item.getName() +
                    " x" + item.getQuantity() +
                    " = " + item.getTotalPrice());
        }
        System.out.println("Total: " + calculateTotalPrice());
    }

    @Override
    public String toString() {
        return "Order status: " + status;
    }

    public void addHistory(String message) {
        history.add(message);
    }

    public void printHistory() {
        System.out.println("\nOrder history:");
        history.printAll();
    }

}
