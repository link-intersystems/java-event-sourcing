package com.link_intersystems.inventory;

import java.util.*;

public class InventoryItemEventList extends AbstractList<InventoryItemEvent> {

    private List<InventoryItemEvent> events = new ArrayList<>();
    private InventoryItemIdentifier identifier;

    public InventoryItemEventList(InventoryItemEvent... events) {
        this(Arrays.asList(events));
    }

    public InventoryItemEventList(Collection<? extends InventoryItemEvent> events) {
        for (InventoryItemEvent event : events) {
            if (identifier == null) {
                identifier = event.getIdentifier();
            }

            if (!Objects.equals(identifier, event.getIdentifier())) {
                throw new IllegalArgumentException("All events in an " + InventoryItemEventList.class.getSimpleName() + " must have the same identifier");
            }

            this.events.add(event);
        }

    }

    public InventoryItemIdentifier getIdentifier() {
        return identifier;
    }

    @Override
    public InventoryItemEvent get(int index) {
        return events.get(index);
    }

    @Override
    public int size() {
        return events.size();
    }

    public void apply(InventoryItem inventoryItem) {
        if (this.identifier == null) {
            return;
        }

        InventoryItemIdentifier identifier = inventoryItem.getIdentifier();
        if (!identifier.equals(this.identifier)) {
            throw new IllegalArgumentException("The events of this " + InventoryItemEventList.class.getSimpleName() + " can not be applied to " + inventoryItem + ", because the identifier differs.");
        }

        stream().forEach(event -> event.apply(inventoryItem));
    }
}
