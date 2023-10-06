package com.link_intersystems.inventory;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return getValue() == quantity.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
