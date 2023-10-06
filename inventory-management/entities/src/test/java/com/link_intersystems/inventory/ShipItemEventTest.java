package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipItemEventTest {

    @Test
    void reduceEvent() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        inventoryItem.setQuantity(new Quantity(10));
        ShipItemEvent shipItemEvent = new ShipItemEvent(new InventoryItemIdentifier("a"), new Quantity(5));

        shipItemEvent.apply(inventoryItem);

        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

}