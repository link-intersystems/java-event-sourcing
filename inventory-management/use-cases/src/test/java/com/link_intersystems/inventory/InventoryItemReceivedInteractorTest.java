package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemReceivedInteractorTest {

    private InventoryItemReceivedInteractor interactor;
    private MockInventoryItemReceivedRepository repository;

    @BeforeEach
    void setUp() {
        repository = new MockInventoryItemReceivedRepository();

        interactor = new InventoryItemReceivedInteractor(repository);
    }

    @Test
    void newInventoryItemReceived() {
        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel();

        requestModel.setItemIdentifier("b");
        requestModel.setReceivedQuantity(10);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("b", responseModel.getItemIdentifier());
        assertEquals(10, responseModel.getActualQuantity());
    }

    @Test
    void itemsReceived() {
        InventoryItemFixture inventoryItemFixture = new InventoryItemFixture();
        List<InventoryItemEvent> events = inventoryItemFixture.getEvents6InStock("a");
        events.forEach(repository::persist);

        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel();

        requestModel.setItemIdentifier("a");
        requestModel.setReceivedQuantity(15);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("a", responseModel.getItemIdentifier());
        assertEquals(21, responseModel.getActualQuantity());

        repository.assertEventPersisted(new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(15)));
    }

}