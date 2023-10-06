package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjustItemEventTest {


    @Test
    void increase() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        AdjustItemEvent adjustEvent = AdjustItemEvent.create(new InventoryItemIdentifier("a"), 5);

        adjustEvent.apply(inventoryItem);

        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

    @Test
    void decrease() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        inventoryItem.setQuantity(new Quantity(10));
        AdjustItemEvent adjustEvent = AdjustItemEvent.create(new InventoryItemIdentifier("a"), -5);

        adjustEvent.apply(inventoryItem);

        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

}