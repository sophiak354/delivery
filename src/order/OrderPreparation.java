package order;

import role.Restaurant;

public abstract class OrderPreparation extends OrderValidator implements OrderAction {
    protected final Restaurant restaurant;
    protected final Order order;

    public OrderPreparation(Restaurant restaurant, Order order) {
        this.restaurant = restaurant;
        this.order = order;
    }

    public abstract void prepare(Order order);

    @Override
    public void execute(Order order) {
        prepare(order);
    }
}
