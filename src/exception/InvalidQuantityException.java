package exception;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Quantity must be greater than zero.");
    }
}
