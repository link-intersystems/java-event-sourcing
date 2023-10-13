package com.link_intersystems.inventory;

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

    @Test
    void apply() {
        InventoryItemEvent event = createInventoryItemEvent("a");
        InventoryItem inventoryItem = mock(InventoryItem.class);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 10, 8, 53, 23);
        Clock clock = Clock.fixed(localDateTime.toInstant(UTC), UTC);

        event.apply(inventoryItem, clock);

        assertEquals(localDateTime, event.getAppliedTime());
    }

    @Test
    void alreadyApplied() {
        InventoryItemEvent event = createInventoryItemEvent("a");
        InventoryItem inventoryItem = mock(InventoryItem.class);

        LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 10, 8, 53, 23);
        Clock clock = Clock.fixed(localDateTime.toInstant(UTC), UTC);

        event.apply(inventoryItem, clock);

        assertThrows(IllegalStateException.class, () -> event.apply(inventoryItem));
    }

    @Test
    void compareTo() {
        InventoryItemEvent event1 = createInventoryItemEvent("a");
        InventoryItemEvent event2 = createInventoryItemEvent("a");
        InventoryItemEvent event3 = createInventoryItemEvent("a");
        InventoryItemEvent event4 = createInventoryItemEvent("a");

        InventoryItem inventoryItem = mock(InventoryItem.class);


        event1.apply(inventoryItem, Clock.fixed(LocalDateTime.of(2023, 9, 10, 8, 53, 23).toInstant(UTC), UTC));
        event3.apply(inventoryItem, Clock.fixed(LocalDateTime.of(2023, 9, 13, 10, 12, 45).toInstant(UTC), UTC));

        List<InventoryItemEvent> events = Arrays.asList(event2, event3, event4, event1);
        Collections.sort(events);

        assertSame(event1, events.get(0));
        assertSame(event3, events.get(1));
        assertSame(event2, events.get(2));
        assertSame(event4, events.get(3));
    }


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

    @Test
    void toStringTest() {
        InventoryItemEvent event = createInventoryItemEvent("a");

        assertNotNull(event.toString());
    }


    private InventoryItemEvent createInventoryItemEvent(String identifier) {
        return new InventoryItemEvent() {
            @Override
            public InventoryItemIdentifier getIdentifier() {
                return new InventoryItemIdentifier(identifier);
            }

            @Override
            public void doApply(InventoryItem inventoryItem) {

            }
        };
    }
}