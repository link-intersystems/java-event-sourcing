package com.link_intersystems.inventory;

import com.link_intersystems.junit.ext.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@UnitTest
class InventoryItemEventListTest {

    private InventoryItemEventMock event1;
    private InventoryItemEventMock event2;
    private InventoryItemEventList events;

    @BeforeEach
    void setUp() {

        event1 = new InventoryItemEventMock("a");
        event2 = new InventoryItemEventMock("a");

        events = new InventoryItemEventList(event1, event2);
    }

    @Test
    void differentIdentifiers() {
        List<InventoryItemEvent> listWithDifferentIdentifiers = new ArrayList<>(events);
        listWithDifferentIdentifiers.add(InventoryItemEventMock.spy("b"));

        assertThrows(IllegalArgumentException.class, () -> new InventoryItemEventList(listWithDifferentIdentifiers));
    }

    @Test
    void getIdentifier() {
        assertEquals(new InventoryItemIdentifier("a"), events.getIdentifier());
    }


    @Test
    void get() {
        assertEquals(event1, events.get(0));
        assertEquals(event2, events.get(1));
    }

    @Test
    void size() {
        assertEquals(2, events.size());
    }

    @Test
    void apply() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        InventoryItemEventList events = new InventoryItemEventList(event1.spy(), event2.spy());

        events.apply(inventoryItem);

        for (InventoryItemEvent event : events) {
            verify(event).apply(inventoryItem);
        }
    }

    @Test
    void applyEmptyList() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("a"));
        new InventoryItemEventList().apply(inventoryItem);
    }

    @Test
    void applyDifferentIdentifier() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("b"));

        assertThrows(IllegalArgumentException.class, () -> events.apply(inventoryItem));
    }
}