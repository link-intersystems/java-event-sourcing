package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemIdentifierTest {

    @Test
    void blankVale() {
        assertThrows(IllegalArgumentException.class, () -> new InventoryItemIdentifier(" "));
    }

    @Test
    void getValue() {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier("abc");

        assertEquals("abc", identifier.getValue());
    }

    @Test
    void testEquals() {
        InventoryItemIdentifier identifier1 = new InventoryItemIdentifier("a");
        InventoryItemIdentifier identifier2 = new InventoryItemIdentifier("a");
        InventoryItemIdentifier identifier3 = new InventoryItemIdentifier("b");

        assertEquals(identifier1, identifier1);
        assertEquals(identifier1, identifier2);
        assertEquals(identifier2, identifier1);
        assertNotEquals(identifier1, identifier3);
    }

    @Test
    void testHashCode() {
        InventoryItemIdentifier identifier1 = new InventoryItemIdentifier("a");
        InventoryItemIdentifier identifier2 = new InventoryItemIdentifier("a");

        assertEquals(identifier1.hashCode(), identifier2.hashCode());
    }
}
