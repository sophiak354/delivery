package com.solvd.delivery.exception;

public class DeliveryNotConfirmedException extends RuntimeException {
    public DeliveryNotConfirmedException() {
        super("Delivery was not confirmed.");
    }
}
