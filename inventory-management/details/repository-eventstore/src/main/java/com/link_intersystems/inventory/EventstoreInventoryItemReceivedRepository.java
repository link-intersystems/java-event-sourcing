package com.link_intersystems.inventory;

import com.eventstore.dbclient.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EventstoreInventoryItemReceivedRepository implements InventoryItemReceivedRepository {

    private final EventStoreDBClient client;

    public EventstoreInventoryItemReceivedRepository(EventStoreDBClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    public InventoryItem findById(InventoryItemIdentifier identifier) {
        ReadStreamOptions readStreamOptions = ReadStreamOptions.get().fromStart().notResolveLinkTos();
        CompletableFuture<ReadResult> readStreamFuture = client.readStream(getStreamName(identifier), readStreamOptions);


        try {
            ReadResult readResult = readStreamFuture.get();
            List<ResolvedEvent> events = readResult.getEvents();

            List<ReceiveItemEvent> receiveItemEvents = events.stream().map(ResolvedEvent::getOriginalEvent).map(ed -> {
                try {
                    return ed.getEventDataAs(ReceivedItemEventData.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).map(this::getEvent).collect(Collectors.toList());

            InventoryItemEventList itemEventList = new InventoryItemEventList(receiveItemEvents);

            InventoryItem inventoryItem = new InventoryItem(identifier);
            itemEventList.apply(inventoryItem);
            return inventoryItem;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public ReceiveItemEvent getEvent(ReceivedItemEventData eventData) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(eventData.getIdentifier());
        Quantity quantityDiff = new Quantity(eventData.getQuantity());
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(identifier, quantityDiff);
        receiveItemEvent.setAppliedTime(eventData.getAppliedTime());
        return receiveItemEvent;
    }

    @Override
    public void persist(ReceiveItemEvent itemEvent) {
        ReceivedItemEventData receivedItemEventData = new ReceivedItemEventData(itemEvent);
        EventData eventData = EventData.builderAsJson("item-received", receivedItemEventData).build();
        client.appendToStream(getStreamName(itemEvent.getIdentifier()), eventData);
    }

    private static String getStreamName(InventoryItemIdentifier identifier) {
        return "item-" + identifier.getValue();
    }
}
