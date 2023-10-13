package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel("b", 10);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("b", responseModel.itemIdentifier());
        assertEquals(10, responseModel.actualQuantity());
    }

    @Test
    void itemsReceived() {
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(6));
        repository.persist(receiveItemEvent);

        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel("a", 15);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("a", responseModel.itemIdentifier());
        assertEquals(21, responseModel.actualQuantity());

        repository.assertEventPersisted(new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(15)));
    }

}