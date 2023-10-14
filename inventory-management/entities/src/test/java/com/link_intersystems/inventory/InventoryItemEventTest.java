package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.time.ZoneOffset.*;
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
    void apply() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 10, 8, 53, 23);
        Clock clock = Clock.fixed(localDateTime.toInstant(UTC), UTC);

        event.apply(item, clock);

        assertEquals(localDateTime, event.getAppliedTime());
    }

    @Test
    void alreadyApplied() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 10, 8, 53, 23);
        Clock clock = Clock.fixed(localDateTime.toInstant(UTC), UTC);

        event.apply(item, clock);

        assertThrows(IllegalStateException.class, () -> event.apply(item));
    }

    @Test
    void compareTo() {
        InventoryItemEvent event1 = new InventoryItemEventMock("a");
        InventoryItemEvent event2 = new InventoryItemEventMock("a");
        InventoryItemEvent event3 = new InventoryItemEventMock("a");
        InventoryItemEvent event4 = new InventoryItemEventMock("a");


        event1.apply(item, Clock.fixed(LocalDateTime.of(2023, 9, 10, 8, 53, 23).toInstant(UTC), UTC));
        event3.apply(item, Clock.fixed(LocalDateTime.of(2023, 9, 13, 10, 12, 45).toInstant(UTC), UTC));

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