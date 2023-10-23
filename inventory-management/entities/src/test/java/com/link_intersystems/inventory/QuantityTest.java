package com.link_intersystems.inventory;

import com.link_intersystems.junit.ext.UnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
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

    @Test
    void testEquals() {
        Quantity quantity1 = new Quantity(5);
        Quantity quantity2 = new Quantity(5);
        Quantity quantity3 = new Quantity(3);

        assertEquals(quantity1, quantity2);
        assertEquals(quantity2, quantity1);
        assertNotEquals(quantity1, quantity3);
    }

    @Test
    void testHashCode() {
        Quantity quantity1 = new Quantity(5);
        Quantity quantity2 = new Quantity(5);

        assertEquals(quantity1.hashCode(), quantity2.hashCode());
    }


}