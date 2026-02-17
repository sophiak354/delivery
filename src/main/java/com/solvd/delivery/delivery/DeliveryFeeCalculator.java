package com.solvd.delivery.delivery;

@FunctionalInterface
public interface DeliveryFeeCalculator {
    double calculateFee(double distanceKm);
}
