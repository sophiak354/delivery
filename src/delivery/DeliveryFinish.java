package delivery;

import order.Order;
import order.OrderStatus;
import role.Courier;
import role.Customer;

public class DeliveryFinish extends DeliveryStep {
    private final Customer customer;

    public DeliveryFinish(Courier courier, Order order, Customer customer) {
        super(courier, order);
        this.customer = customer;
    }

    @Override
    public void doStep(Order order) {

        if (!validateStatus(order, "On the way", "finish delivering")) {
            return;
        }

        order.setStatus(OrderStatus.DELIVERED);
        System.out.println("The order is delivered to: " +
                customer.getAddress() + " for " +
                customer.getName() + "."
        );
        notifyUser("Order status is changed by " +
                courier.getName() + ". " + order);
    }

    @Override
    public void notifyUser(String message) {
        System.out.println(message);
    }
}
