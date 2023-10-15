package com.link_intersystems.inventory;

import java.time.LocalDateTime;

public class AdjustedItemEventData extends AbstractItemEventData implements ItemEventData {

    public AdjustedItemEventData() {
    }

    public AdjustedItemEventData(AdjustItemEvent event) {
        setIdentifier(event.getIdentifier().getValue());
        setQuantity(event.getQuantity().getValue());
        setAppliedTime(event.getAppliedTime().toString());
    }

    @Override
    public InventoryItemEvent toDomain() {
        InventoryItemIdentifier itemIdentifier = new InventoryItemIdentifier(getIdentifier());
        AdjustItemEvent event = AdjustItemEvent.create(itemIdentifier, getQuantity());
        event.setAppliedTime(LocalDateTime.parse(getAppliedTime()));
        return event;
    }
}
