package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiveItemEventTest {


    @Test
    void applyAddEvent() {
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(5));

        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));

        receiveItemEvent.apply(inventoryItem);


        assertEquals(5, inventoryItem.getQuantity().getValue());
    }

}