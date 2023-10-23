package com.link_intersystems.inventory;

import com.link_intersystems.junit.ext.UnitTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class QuantityEventTest {

    @Test
    void apply() {
        InventoryItem.QuantityEvent event = createEvent("a", 5);

        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        inventoryItem.setQuantity(new Quantity(3));

        event.apply(inventoryItem);

        assertEquals(15, inventoryItem.getQuantity().getValue());
    }

    @Test
    void applyWrongIdentifier() {
        InventoryItem.QuantityEvent event = createEvent("a", 5);
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("b"));

        assertThrows(IllegalArgumentException.class, () -> event.apply(inventoryItem));
    }


    @Test
    void testEquals() {
        InventoryItem.QuantityEvent event1 = createEvent("a", 5);
        InventoryItem.QuantityEvent event2 = createEvent("a", 5);
        InventoryItem.QuantityEvent event3 = createEvent("a", 6);
        InventoryItem.QuantityEvent event4 = createEvent("b", 5);

        assertEquals(event1, event2);
        assertEquals(event2, event1);

        assertNotEquals(event1, event3);
        assertNotEquals(event1, event4);
    }

    @Test
    void testHashCode() {
        InventoryItem.QuantityEvent event1 = createEvent("a", 5);
        InventoryItem.QuantityEvent event2 = createEvent("a", 5);

        assertEquals(event1.hashCode(), event2.hashCode());
    }

    private InventoryItem.QuantityEvent createEvent(String identifier, int quantity) {
        return new InventoryItem.QuantityEvent(new InventoryItemIdentifier(identifier), new Quantity(quantity)) {
            @Override
            protected Quantity getNewQuantity(InventoryItem inventoryItem, Quantity quantityDiff) {
                return new Quantity(inventoryItem.getQuantity().getValue() * quantityDiff.getValue());
            }
        };
    }

}