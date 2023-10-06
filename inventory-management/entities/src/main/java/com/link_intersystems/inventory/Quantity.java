package com.link_intersystems.inventory;

public class Quantity {

    private int value;

    public Quantity(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("quantity must be 0 or greater, but was " + value);
        }
        this.value = value;
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(getValue() + quantity.getValue());
    }

    public int getValue() {
        return value;
    }

    public Quantity substract(Quantity quantity) {
        return new Quantity(getValue() - quantity.getValue());
    }
}
