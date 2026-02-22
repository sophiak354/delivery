package com.solvd.delivery.order;

import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.util.SimpleLinkedList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    private OrderStatus status;
    private final List<OrderItem> items;
    private final SimpleLinkedList<OrderHistoryEntry> history = new SimpleLinkedList<>();

    public Order() {
        this.status = OrderStatus.NOT_SET;
        this.items = new ArrayList<>();
    }

    public void addItem(Offer offer, int quantity) {
        OrderItem item = new OrderItem(offer, quantity);
        items.add(item);
    }

    public double calculateTotalPrice() {
        double total = 0;

        for (OrderItem item : items) {
            total += item.totalPrice();
        }
        return total;
    }

    public void printOrder() {

        for (OrderItem item : items) {
            System.out.println(item.offer().getType().offerType() + ": " + item.offer().getName() +
                    " x" + item.quantity() +
                    " = " + item.totalPrice());
        }
        System.out.println("Total: " + calculateTotalPrice());
    }

    @Override
    public String toString() {
        return "Order status: " + status.status();
    }

    public void addHistory(String message) {
        history.add(new OrderHistoryEntry(LocalDateTime.now(), message));
    }

    public void printHistory() {
        System.out.println("\nOrder history:");
        history.printAll();
    }

}
