package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryItemEventListTest {

    private InventoryItemEvent event1;
    private InventoryItemEvent event2;
    private InventoryItemEventList events;

    @BeforeEach
    void setUp() {
        event1 = itemEvent("a");
        event2 = itemEvent("a");

        events = new InventoryItemEventList(event1, event2);
    }

    private InventoryItemEvent itemEvent(String identifier) {
        InventoryItemEvent event = mock(InventoryItemEvent.class);
        when(event.getIdentifier()).thenReturn(new InventoryItemIdentifier(identifier));
        return event;
    }

    @Test
    void differentIdentifiers() {
        List<InventoryItemEvent> listWithDifferentIdentifiers = new ArrayList<>(events);
        listWithDifferentIdentifiers.add(itemEvent("b"));

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

        events.apply(inventoryItem);

        verify(event1).apply(inventoryItem);
        verify(event2).apply(inventoryItem);
    }

    @Test
    void applyDifferentIdentifier() {
        InventoryItem inventoryItem = new InventoryItem(new InventoryItemIdentifier("b"));

        assertThrows(IllegalArgumentException.class, () -> events.apply(inventoryItem));
    }
}