package com.solvd.delivery.exception;

public class PaymentNotCompletedException extends RuntimeException {
    public PaymentNotCompletedException() {
        super("Payment failed. Not enough money.");
    }
}
