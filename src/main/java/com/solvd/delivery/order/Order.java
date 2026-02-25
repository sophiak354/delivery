package com.solvd.delivery.order;

import com.solvd.delivery.offer.Offer;
import com.solvd.delivery.util.SimpleLinkedList;
import com.solvd.delivery.util.reflection.DefaultValue;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    @DefaultValue("NOT_SET")
    private OrderStatus status;
    private final List<OrderItem> items;
    private final SimpleLinkedList<OrderHistoryEntry> history = new SimpleLinkedList<>();

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(Offer offer, int quantity) {
        OrderItem item = new OrderItem(offer, quantity);
        items.add(item);
    }

    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::totalPrice)
                .sum();
    }

    public void printOrder() {
        items.forEach(item ->
                        System.out.println(
                                item.offer().getType().offerType() + ": "
                                        + item.offer().getName()
                                        + " x" + item.quantity()
                                        + " = " + item.totalPrice()
                        )
                );
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
