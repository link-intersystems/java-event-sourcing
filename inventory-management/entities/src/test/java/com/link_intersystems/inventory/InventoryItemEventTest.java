package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryItemEventTest {

    private InventoryItemEvent event;
    private InventoryItem item;

    @BeforeEach
    void setUp() {
        event = new InventoryItemEventMock("a");
        item = mock(InventoryItem.class);
    }

    @Test
    void setAppliedTime() {
        InventoryItemEventMock event = new InventoryItemEventMock("a");

        LocalDateTime now = LocalDateTime.now();
        event.setAppliedTime(now);

        assertEquals(now, event.getAppliedTime());

        assertThrows(IllegalStateException.class, () -> event.setAppliedTime(LocalDateTime.now()));
    }

    @Test
    void compareTo() {
        InventoryItemEvent event1 = new InventoryItemEventMock("a");
        InventoryItemEvent event2 = new InventoryItemEventMock("a");
        InventoryItemEvent event3 = new InventoryItemEventMock("a");
        InventoryItemEvent event4 = new InventoryItemEventMock("a");


        event1.setAppliedTime(LocalDateTime.of(2023, 9, 10, 8, 53, 23));
        event3.setAppliedTime(LocalDateTime.of(2023, 9, 13, 10, 12, 45));

        List<InventoryItemEvent> events = Arrays.asList(event2, event3, event4, event1);
        Collections.sort(events);

        assertSame(event1, events.get(0));
        assertSame(event3, events.get(1));
        assertSame(event2, events.get(2));
        assertSame(event4, events.get(3));
    }


    @Test
    void testEquals() {
        InventoryItemEvent equalEvent = new InventoryItemEventMock("a");
        InventoryItemEvent unequalEvent = new InventoryItemEventMock("b");

        assertEquals(event, equalEvent);
        assertEquals(equalEvent, event);
        assertNotEquals(equalEvent, unequalEvent);
    }

    @Test
    void testHashCode() {
        InventoryItemEvent otherEvent = new InventoryItemEventMock("a");

        assertEquals(event.hashCode(), otherEvent.hashCode());
    }

    @Test
    void toStringTest() {
        assertNotNull(event.toString());
    }
}