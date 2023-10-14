package com.link_intersystems.inventory;

import org.mockito.Mockito;

public class InventoryItemEventMock extends InventoryItemEvent {

    public static InventoryItemEvent spy(String identifier) {
        return new InventoryItemEventMock(identifier).spy();
    }

    public InventoryItemEventMock(String identifier) {
        super(new InventoryItemIdentifier(identifier));
    }

    @Override
    protected void doApply(InventoryItem inventoryItem) {
    }

    public InventoryItemEvent spy() {
        return Mockito.spy(this);
    }

}
