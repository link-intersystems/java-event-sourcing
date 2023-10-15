package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemReceivedInteractorTest {

    private InventoryItemReceivedInteractor interactor;
    private MockInventoryItemReceivedRepository repository;
    private ClockMock clockMock;

    @BeforeEach
    void setUp() {
        clockMock = new ClockMock();
        repository = new MockInventoryItemReceivedRepository(clockMock);

        interactor = new InventoryItemReceivedInteractor(repository);
    }

    @Test
    void newInventoryItemReceived() {
        clockMock.add(LocalDateTime.of(2023, 9, 5, 10, 45, 23, 12));
        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel("b", 10);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("b", responseModel.itemIdentifier());
        assertEquals(10, responseModel.actualQuantity());
    }

    @Test
    void additionalItemsReceived() {
        clockMock.add(LocalDateTime.of(2023, 9, 5, 10, 45, 23, 12));
        clockMock.add(LocalDateTime.of(2023, 9, 8, 3, 41, 32, 32));
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(6));
        repository.persist(receiveItemEvent);

        InventoryItemReceivedUseCase.RequestModel requestModel = new InventoryItemReceivedUseCase.RequestModel("a", 15);

        InventoryItemReceivedUseCase.ResponseModel responseModel = interactor.itemsReceived(requestModel);

        assertEquals("a", responseModel.itemIdentifier());
        assertEquals(21, responseModel.actualQuantity());

        ReceiveItemEvent expectedEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(15));
        expectedEvent.setAppliedTime(LocalDateTime.of(2023, 9, 8, 3, 41, 32, 32));
        repository.assertEventPersisted(expectedEvent);
    }

}