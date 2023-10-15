package com.link_intersystems.inventory;

import com.eventstore.dbclient.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class EventstoreInventoryItemRepository implements InventoryItemReceivedRepository, InventoryItemAdjustedRepository {

    private InventoryItemEventMapper mapper = new InventoryItemEventMapper();

    private final EventStoreDBClient client;

    public EventstoreInventoryItemRepository(EventStoreDBClient client) {
        this.client = Objects.requireNonNull(client);
    }

    @Override
    public InventoryItem findById(InventoryItemIdentifier identifier) {
        ReadStreamOptions readStreamOptions = ReadStreamOptions.get().fromStart().notResolveLinkTos();
        CompletableFuture<ReadResult> readStreamFuture = client.readStream(getStreamName(identifier), readStreamOptions);


        FutureHandler<ReadResult> futureHandler = new FutureHandler<>(readStreamFuture);
        InventoryItemEventList inventoryItemEvents = futureHandler.mapWhenPresent(this::readEvents);

        InventoryItem inventoryItem = new InventoryItem(identifier);
        inventoryItemEvents.apply(inventoryItem);
        return inventoryItem;
    }

    private InventoryItemEventList readEvents(ReadResult readResult) {
        List<InventoryItemEvent> events = readResult.getEvents().stream()
                .map(ResolvedEvent::getOriginalEvent)
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new InventoryItemEventList(events);
    }

    @Override
    public void persist(AdjustItemEvent itemEvent) {
        this.persist((InventoryItemEvent) itemEvent);
    }

    @Override
    public void persist(ReceiveItemEvent itemEvent) {
        this.persist((InventoryItemEvent) itemEvent);
    }

    private void persist(InventoryItemEvent itemEvent) {
        itemEvent.setAppliedTime(LocalDateTime.now());
        EventData eventData = mapper.toEventData(itemEvent);
        CompletableFuture<WriteResult> resultFuture = client.appendToStream(getStreamName(itemEvent.getIdentifier()), eventData);

        FutureHandler<WriteResult> futureHandler = new FutureHandler<>(resultFuture);
        futureHandler.waitForResult();
    }

    private static String getStreamName(InventoryItemIdentifier identifier) {
        return "item-" + identifier.getValue();
    }
}
