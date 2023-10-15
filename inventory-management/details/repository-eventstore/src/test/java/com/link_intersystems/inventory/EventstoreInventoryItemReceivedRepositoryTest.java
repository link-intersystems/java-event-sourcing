package com.link_intersystems.inventory;

import com.link_intersystems.testcontainers.eventsourcedb.EventsourceDBTestcontainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class EventstoreInventoryItemReceivedRepositoryTest {

    @Container
    public EventsourceDBTestcontainer eventstoreContainer = new EventsourceDBTestcontainer();
    private EventstoreInventoryItemReceivedRepository repository;

    @BeforeEach
    void setUp() {
        repository = new EventstoreInventoryItemReceivedRepository(eventstoreContainer.getClient());
    }

    @Test
    void receiveItems() {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier("a");
        repository.persist(new ReceiveItemEvent(identifier, new Quantity(10)));
        repository.persist(new ReceiveItemEvent(identifier, new Quantity(8)));


        InventoryItem inventoryItem = repository.findById(identifier);

        assertEquals(18, inventoryItem.getQuantity().getValue());
    }
}
