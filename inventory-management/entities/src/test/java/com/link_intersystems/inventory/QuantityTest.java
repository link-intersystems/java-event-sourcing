package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    void wrongValue() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(-1));
    }

    @Test
    void getValue() {
        Quantity quantity = new Quantity(5);

        assertEquals(5, quantity.getValue());
    }

    @Test
    void add() {
        Quantity quantity = new Quantity(5);

        Quantity result = quantity.add(new Quantity(10));

        assertNotSame(result, quantity);
        assertEquals(15, result.getValue());
    }

    @Test
    void substract() {
        Quantity quantity = new Quantity(10);

        Quantity result = quantity.substract(new Quantity(5));

        assertNotSame(result, quantity);
        assertEquals(5, result.getValue());
    }
}