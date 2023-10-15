package com.link_intersystems.inventory;

public interface InventoryItemShippedRepository {
    void persist(InventoryItemEvent itemEvent);

    InventoryItem findById(InventoryItemIdentifier identifier);
}
