package com.link_intersystems.inventory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MockInventoryItemReceivedRepository implements InventoryItemReceivedRepository {

    private Map<InventoryItemIdentifier, List<InventoryItemEvent>> eventStore = new HashMap<>();

    @Override
    public InventoryItem findById(InventoryItemIdentifier identifier) {
        List<InventoryItemEvent> inventoryItemEvents = eventStore.get(identifier);

        if (inventoryItemEvents == null) {
            return null;
        }

        InventoryItem inventoryItem = new InventoryItem(identifier);
        Collections.sort(inventoryItemEvents);
        new InventoryItemEventList(inventoryItemEvents).apply(inventoryItem);
        return inventoryItem;
    }

    @Override
    public void persist(InventoryItemEvent itemEvent) {
        InventoryItemIdentifier identifier = itemEvent.getIdentifier();
        List<InventoryItemEvent> inventoryItemEvents = eventStore.computeIfAbsent(identifier, id -> new ArrayList<>());
        inventoryItemEvents.add(itemEvent);
    }

    public void assertEventPersisted(InventoryItemEvent event) {
        InventoryItemIdentifier identifier = event.getIdentifier();

        List<InventoryItemEvent> itemEvents = eventStore.get(identifier);
        assertNotNull(itemEvents, () -> "item events " + identifier);

        assertTrue(itemEvents.contains(event), () -> event + " persisted");
    }
}
