package com.link_intersystems.inventory;

public class ShipItemEvent extends AdjustItemEvent {

    public ShipItemEvent(InventoryItemIdentifier identifier, Quantity quantityDiff) {
        super(identifier, AdjustOperation.DECREASE, quantityDiff);
    }
}
