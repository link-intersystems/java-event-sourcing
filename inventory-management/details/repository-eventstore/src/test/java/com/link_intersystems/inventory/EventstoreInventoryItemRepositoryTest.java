package com.link_intersystems.inventory;

import com.link_intersystems.testcontainers.eventsourcedb.EventsourceDBTestcontainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class EventstoreInventoryItemRepositoryTest {

    @Container
    public EventsourceDBTestcontainer eventstoreContainer = new EventsourceDBTestcontainer();
    private EventstoreInventoryItemRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EventstoreInventoryItemRepository(eventstoreContainer.getClient());
    }

    @Test
    void persistAndLoad() {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier("a");
        repository.persist(new ReceiveItemEvent(identifier, new Quantity(10)));
        repository.persist(AdjustItemEvent.create(identifier, 4));
        repository.persist(new ShipItemEvent(identifier, new Quantity(8)));


        InventoryItem inventoryItem = repository.findById(identifier);

        assertEquals(6, inventoryItem.getQuantity().getValue());
    }
}
