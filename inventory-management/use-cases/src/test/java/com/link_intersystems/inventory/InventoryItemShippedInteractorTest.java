package com.link_intersystems.inventory;

import com.link_intersystems.junit.ext.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class InventoryItemShippedInteractorTest {

    private InventoryItemShippedInteractor interactor;
    private MockRepository repository;
    private ClockMock clockMock;

    @BeforeEach
    void setUp() {
        clockMock = new ClockMock();
        repository = new MockRepository(clockMock);
        interactor = new InventoryItemShippedInteractor(repository);
    }

    @Test
    void shipItems() {
        clockMock.add(LocalDateTime.of(2023, 9, 5, 10, 45, 23, 12));
        clockMock.add(LocalDateTime.of(2023, 9, 8, 3, 41, 32, 32));
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(20));
        repository.persist(receiveItemEvent);

        InventoryItemShippedUseCase.RequestModel requestModel = new InventoryItemShippedUseCase.RequestModel("a", 10);

        InventoryItemShippedUseCase.ResponseModel responseModel = interactor.shipItems(requestModel);

        assertEquals("a", responseModel.identifier());
        assertEquals(10, responseModel.actualQuantity());
    }
}