package com.link_intersystems.inventory;

public class ReceiveItemEvent extends AdjustItemEvent {

    public ReceiveItemEvent(InventoryItemIdentifier identifier, Quantity quantityDiff) {
        super(identifier, AdjustOperation.INCREASE, quantityDiff);
    }
}
