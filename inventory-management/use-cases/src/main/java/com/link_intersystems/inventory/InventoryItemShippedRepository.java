package com.link_intersystems.inventory;

public interface InventoryItemShippedRepository {
    void persist(ShipItemEvent itemEvent);

    InventoryItem findById(InventoryItemIdentifier identifier);
}
