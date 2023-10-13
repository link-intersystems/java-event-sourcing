package com.link_intersystems.inventory;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class InventoryItemEvent implements Comparable<InventoryItemEvent> {

    private LocalDateTime appliedTime;

    public abstract InventoryItemIdentifier getIdentifier();

    public LocalDateTime getAppliedTime() {
        return appliedTime;
    }

    public void apply(InventoryItem inventoryItem) {
        apply(inventoryItem, Clock.systemDefaultZone());
    }

    public void apply(InventoryItem inventoryItem, Clock clock) {
        if (appliedTime != null) {
            throw new IllegalStateException("Event already applied");
        }

        doApply(inventoryItem);

        this.appliedTime = LocalDateTime.now(clock);
    }

    protected abstract void doApply(InventoryItem inventoryItem);

    @Override
    public int compareTo(InventoryItemEvent o) {
        if (getAppliedTime() != null && o.getAppliedTime() != null) {
            return getAppliedTime().compareTo(o.getAppliedTime());
        }

        if (getAppliedTime() == null && o.getAppliedTime() == null) {
            return 0;
        }

        if (getAppliedTime() == null) {
            return 1;
        }

        return -1;
    }

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

    @Override
    public String toString() {
        return "InventoryItemEvent{" +
                "identifier=" + getIdentifier() + ", " +
                "appliedTime=" + appliedTime +
                '}';
    }
}
