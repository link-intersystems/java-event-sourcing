package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InventoryItemAdjustedInteractorTest {

    private InventoryItemAdjustedInteractor interactor;
    private MockRepository repository;
    private ClockMock clockMock;

    @BeforeEach
    void setUp() {
        clockMock = new ClockMock();
        repository = new MockRepository(clockMock);
        interactor = new InventoryItemAdjustedInteractor(repository);
    }

    @Test
    void adjustItems() {
        clockMock.add(LocalDateTime.of(2023, 9, 5, 10, 45, 23, 12));
        InventoryItemAdjustedUseCase.RequestModel requestModel = new InventoryItemAdjustedUseCase.RequestModel("b", 10);

        InventoryItemAdjustedUseCase.ResponseModel responseModel = interactor.adjustItems(requestModel);

        assertEquals("b", responseModel.identifier());
        assertEquals(10, responseModel.actualQuantity());
    }
}