package com.link_intersystems.inventory;

public interface InventoryItemAdjustedRepository {
    void persist(AdjustItemEvent itemEvent);

    InventoryItem findById(InventoryItemIdentifier identifier);
}
