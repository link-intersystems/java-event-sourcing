package com.link_intersystems.inventory;

import com.eventstore.dbclient.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class EventstoreInventoryItemReceivedRepository implements InventoryItemReceivedRepository {

    private ReceivedItemEventMapper mapper = new ReceivedItemEventMapper();

    private final EventStoreDBClient client;

    public EventstoreInventoryItemReceivedRepository(EventStoreDBClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    public InventoryItem findById(InventoryItemIdentifier identifier) {
        ReadStreamOptions readStreamOptions = ReadStreamOptions.get().fromStart().notResolveLinkTos();
        CompletableFuture<ReadResult> readStreamFuture = client.readStream(getStreamName(identifier), readStreamOptions);


        FutureHandler<ReadResult> futureHandler = new FutureHandler<>(readStreamFuture);
        return futureHandler.mapWhenPresent(rr -> readInventoryItem(identifier, rr));
    }

    private InventoryItem readInventoryItem(InventoryItemIdentifier identifier, ReadResult readResult) {
        List<ResolvedEvent> events = readResult.getEvents();

        InventoryItem inventoryItem = new InventoryItem(identifier);

        events.stream()
                .map(mapper::toDomain)
                .forEach(event -> event.apply(inventoryItem));

        return inventoryItem;
    }

    @Override
    public void persist(ReceiveItemEvent itemEvent) {
        ReceivedItemEventData receivedItemEventData = mapper.toEventData(itemEvent);
        EventData eventData = EventData.builderAsJson("item-received", receivedItemEventData).build();
        client.appendToStream(getStreamName(itemEvent.getIdentifier()), eventData);
    }

    private static String getStreamName(InventoryItemIdentifier identifier) {
        return "item-" + identifier.getValue();
    }
}
