package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdjustItemEventTest {


    @Test
    void increase() {
        AdjustItemEvent adjustEvent = AdjustItemEvent.create(new InventoryItemIdentifier("a"), 5);
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));

        adjustEvent.apply(inventoryItem);

        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

    @Test
    void decrease() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        inventoryItem.setQuantity(new Quantity(5));

        AdjustItemEvent adjustEvent = AdjustItemEvent.create(new InventoryItemIdentifier("a"), -3);

        adjustEvent.apply(inventoryItem);

        assertEquals(2, inventoryItem.getQuantity().getValue());
    }

}