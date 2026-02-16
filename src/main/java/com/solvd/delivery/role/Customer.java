package com.solvd.delivery.role;

public class Customer extends Role {
    private final String address;

    public Customer(String name, String address) {
        super(name);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
