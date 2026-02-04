package exception;

public class DeliveryNotConfirmedException extends RuntimeException {
    public DeliveryNotConfirmedException() {
        super("Delivery was not confirmed.");
    }
}
