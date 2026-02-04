package exception;

public class PaymentNotCompletedException extends RuntimeException {
    public PaymentNotCompletedException() {
        super("Payment failed. Not enough money.");
    }
}
