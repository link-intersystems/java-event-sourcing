package com.link_intersystems.inventory;

import java.time.LocalDateTime;

public class ReceivedItemEventData extends AbstractItemEventData implements ItemEventData {

    public ReceivedItemEventData() {
    }

    public ReceivedItemEventData(ReceiveItemEvent event) {
        setIdentifier(event.getIdentifier().getValue());
        setQuantity(event.getQuantity().getValue());
        setAppliedTime(event.getAppliedTime().toString());
    }

    @Override
    public InventoryItemEvent toDomain() {
        InventoryItemIdentifier itemIdentifier = new InventoryItemIdentifier(getIdentifier());
        ReceiveItemEvent event = new ReceiveItemEvent(itemIdentifier, new Quantity(getQuantity()));
        event.setAppliedTime(LocalDateTime.parse(getAppliedTime()));
        return event;
    }
}
