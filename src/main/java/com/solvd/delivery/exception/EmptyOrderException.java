package com.solvd.delivery.exception;

public class EmptyOrderException extends RuntimeException {
    public EmptyOrderException() {
        super("Your order is empty.");
    }
}
