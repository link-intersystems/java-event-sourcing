package com.link_intersystems.inventory;

import java.time.LocalDateTime;

public class ShipItemEventData extends AbstractItemEventData implements ItemEventData {

    public ShipItemEventData() {
    }

    public ShipItemEventData(ShipItemEvent event) {
        setIdentifier(event.getIdentifier().getValue());
        setQuantity(event.getQuantity().getValue());
        setAppliedTime(event.getAppliedTime().toString());
    }

    @Override
    public InventoryItemEvent toDomain() {
        InventoryItemIdentifier itemIdentifier = new InventoryItemIdentifier(getIdentifier());
        ShipItemEvent event = new ShipItemEvent(itemIdentifier, new Quantity(getQuantity()));
        event.setAppliedTime(LocalDateTime.parse(getAppliedTime()));
        return event;
    }
}
