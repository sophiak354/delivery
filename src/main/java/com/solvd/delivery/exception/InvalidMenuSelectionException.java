package com.solvd.delivery.exception;

public class InvalidMenuSelectionException extends RuntimeException {
    public InvalidMenuSelectionException() {
        super("Invalid menu option selected.");
    }
}