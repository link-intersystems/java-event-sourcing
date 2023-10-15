package com.link_intersystems.inventory;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.*;

public abstract class InventoryItemEvent implements Comparable<InventoryItemEvent> {

    private InventoryItemIdentifier identifier;
    private LocalDateTime appliedTime;


    public InventoryItemEvent(InventoryItemIdentifier identifier) {
        this.identifier = requireNonNull(identifier);
    }

    public InventoryItemIdentifier getIdentifier() {
        return identifier;
    }

    public void setAppliedTime(LocalDateTime appliedTime) {
        if(this.appliedTime != null){
            throw new IllegalStateException("Event already applied");
        }
        this.appliedTime = appliedTime;
    }

    public LocalDateTime getAppliedTime() {
        return appliedTime;
    }


    public abstract void apply(InventoryItem inventoryItem);

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
        if (!Objects.equals(getIdentifier(), inventoryItemEvent.getIdentifier())) {
            return false;
        }

        return Objects.equals(getAppliedTime(), ((InventoryItemEvent) o).getAppliedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getAppliedTime());
    }

    @Override
    public String toString() {
        return "InventoryItemEvent{" +
                "identifier=" + getIdentifier() + ", " +
                "appliedTime=" + appliedTime +
                '}';
    }


}
