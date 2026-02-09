package delivery;

import order.Order;
import order.OrderPreparation;
import order.OrderStatus;
import role.Restaurant;
import util.Delayable;
import util.Notifiable;

public class Cooking extends OrderPreparation implements Delayable, Notifiable {

    public Cooking(Restaurant restaurant, Order order) {
        super(restaurant, order);
    }

    @Override
    public void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public void prepare(Order order) {

        if (!validateStatus(order, OrderStatus.DELIVERY_CONFIRMED, "start cooking")) {
            return;
        }

        order.setStatus(OrderStatus.COOKING);
        notifyUser("Order status is changed by " + restaurant.getName() + ". " + order);
        order.addHistory("Status changed to Cooking.");

        System.out.println("Cooking is in progress...");

        delay();

        order.setStatus(OrderStatus.COOKED);
        notifyUser("Order status is changed by " + restaurant.getName() + ". " + order);
        order.addHistory("Status changed to Cooked.");
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
