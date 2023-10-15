package com.link_intersystems.inventory;

public interface InventoryItemReceivedRepository {
    InventoryItem findById(InventoryItemIdentifier identifier);

    void persist(ReceiveItemEvent itemEvent);
}
