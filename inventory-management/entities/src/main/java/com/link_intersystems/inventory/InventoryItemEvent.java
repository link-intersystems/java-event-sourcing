package com.link_intersystems.inventory;

import java.util.Objects;

public abstract class InventoryItemEvent {

    public abstract InventoryItemIdentifier getIdentifier();

    public abstract void apply(InventoryItem inventoryItem);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItemEvent inventoryItemEvent = (InventoryItemEvent) o;
        return Objects.equals(getIdentifier(), inventoryItemEvent.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

}
