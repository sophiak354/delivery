package com.solvd.delivery.delivery;

public class DeliveryFeeService {

    private final DeliveryFeeCalculator standardFee =
            d -> 40 + d * 5;
    private final DeliveryFeeCalculator expressFee =
            d -> 80 + d * 8;
    private final DeliveryFeeCalculator promoFee =
            d -> d >= 5 ? 0 : 40 + d * 5;

    public double calculateStandard(double distance) {
        return standardFee.calculateFee(distance);
    }

    public double calculateExpress(double distance) {
        return expressFee.calculateFee(distance);
    }

    public double calculatePromo(double distance) {
        return promoFee.calculateFee(distance);
    }
}
