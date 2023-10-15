package com.link_intersystems.inventory;

public interface InventoryItemAdjustedRepository {
    void persist(InventoryItemEvent itemEvent);

    InventoryItem findById(InventoryItemIdentifier identifier);
}
