package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemTest {

    private InventoryItem inventoryItem;

    @BeforeEach
    void setUp() {
        inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
    }

    @Test
    void getIdentifier() {
        assertEquals(new InventoryItemIdentifier("a"), inventoryItem.getIdentifier());
    }

    @Test
    void setQuantity() {
        assertEquals(new Quantity(0), inventoryItem.getQuantity());

        inventoryItem.setQuantity(new Quantity(5));

        assertEquals(new Quantity(5), inventoryItem.getQuantity());

    }
}