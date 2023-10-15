package com.link_intersystems.inventory;

import com.eventstore.dbclient.RecordedEvent;
import com.eventstore.dbclient.ResolvedEvent;

import java.io.IOException;

public class ReceivedItemEventMapper {

    public ReceiveItemEvent toDomain(ResolvedEvent resolvedEvent) {
        RecordedEvent recordedEvent = resolvedEvent.getOriginalEvent();
        ReceivedItemEventData eventData = mapRecordedEvent(recordedEvent);
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(eventData.getIdentifier());
        Quantity quantityDiff = new Quantity(eventData.getQuantity());
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(identifier, quantityDiff);
        receiveItemEvent.setAppliedTime(eventData.getAppliedTime());
        return receiveItemEvent;
    }

    ReceivedItemEventData mapRecordedEvent(RecordedEvent recordedEvent) {
        try {
            return recordedEvent.getEventDataAs(ReceivedItemEventData.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ReceivedItemEventData toEventData(ReceiveItemEvent itemEvent) {
        ReceivedItemEventData eventData = new ReceivedItemEventData();
        eventData.setIdentifier(itemEvent.getIdentifier().getValue());
        eventData.setAppliedTime(itemEvent.getAppliedTime());
        eventData.setQuantity(itemEvent.getQuantity().getValue());
        return eventData;
    }
}
