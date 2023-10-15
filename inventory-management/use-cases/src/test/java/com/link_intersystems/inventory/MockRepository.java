package com.link_intersystems.inventory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.*;
import static org.junit.jupiter.api.Assertions.*;

class MockRepository implements InventoryItemReceivedRepository, InventoryItemAdjustedRepository, InventoryItemShippedRepository {

    private Map<InventoryItemIdentifier, SortedSet<InventoryItemEvent>> eventStore = new HashMap<>();

    private Clock clock;

    public MockRepository() {
        this(Clock.systemDefaultZone());
    }

    public MockRepository(Clock clock) {
        this.clock = requireNonNull(clock);
    }

    @Override
    public InventoryItem findById(InventoryItemIdentifier identifier) {
        SortedSet<InventoryItemEvent> inventoryItemEvents = getEvents(identifier);

        InventoryItem inventoryItem = new InventoryItem(identifier);
        InventoryItemEventList eventList = new InventoryItemEventList(inventoryItemEvents);
        eventList.apply(inventoryItem);
        return inventoryItem;
    }

    @Override
    public void persist(ShipItemEvent itemEvent) {
        persist((InventoryItemEvent) itemEvent);
    }

    @Override
    public void persist(AdjustItemEvent itemEvent) {
        persist((InventoryItemEvent) itemEvent);
    }

    @Override
    public void persist(ReceiveItemEvent itemEvent) {
        persist((InventoryItemEvent) itemEvent);
    }

    public void persist(InventoryItemEvent itemEvent) {
        InventoryItemIdentifier identifier = itemEvent.getIdentifier();
        SortedSet<InventoryItemEvent> events = getEvents(identifier);
        events.add(itemEvent);
        itemEvent.setAppliedTime(LocalDateTime.now(clock));
    }

    private SortedSet<InventoryItemEvent> getEvents(InventoryItemIdentifier identifier) {
        return eventStore.computeIfAbsent(identifier, id -> new TreeSet<>());
    }

    public void assertEventPersisted(InventoryItemEvent event) {
        InventoryItemIdentifier identifier = event.getIdentifier();

        SortedSet<InventoryItemEvent> itemEvents = eventStore.get(identifier);
        assertNotNull(itemEvents, () -> "item events " + identifier);

        assertTrue(itemEvents.contains(event), () -> event + " persisted");
    }
}
