package com.link_intersystems.inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemEventTest {

    @Test
    void testEquals() {
        InventoryItemEvent event1 = createInventoryItemEvent("a");
        InventoryItemEvent event2 = createInventoryItemEvent("a");
        InventoryItemEvent event3 = createInventoryItemEvent("b");

        assertEquals(event1, event2);
        assertEquals(event2, event1);
        assertNotEquals(event1, event3);
    }

    @Test
    void testHashCode() {
        InventoryItemEvent event1 = createInventoryItemEvent("a");
        InventoryItemEvent event2 = createInventoryItemEvent("a");

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    private InventoryItemEvent createInventoryItemEvent(String identifier) {
        return new InventoryItemEvent() {
            @Override
            public InventoryItemIdentifier getIdentifier() {
                return new InventoryItemIdentifier(identifier);
            }

            @Override
            public void apply(InventoryItem inventoryItem) {

            }
        };
    }
}