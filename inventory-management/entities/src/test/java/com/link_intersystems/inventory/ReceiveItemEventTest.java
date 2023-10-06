package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiveItemEventTest {


    @Test
    void applyAddEvent() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(5));

        receiveItemEvent.apply(inventoryItem);

        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

}