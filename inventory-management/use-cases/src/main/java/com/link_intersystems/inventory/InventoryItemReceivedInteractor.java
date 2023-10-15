package com.link_intersystems.inventory;

import static java.util.Objects.*;

public class InventoryItemReceivedInteractor implements InventoryItemReceivedUseCase {

    private InventoryItemReceivedRepository repository;

    public InventoryItemReceivedInteractor(InventoryItemReceivedRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public ResponseModel itemsReceived(RequestModel requestModel) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(requestModel.identifier());
        Quantity receivedQuantity = new Quantity(requestModel.receivedQuantity());

        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(identifier, receivedQuantity);
        repository.persist(receiveItemEvent);

        InventoryItem inventoryItem = repository.findById(identifier);

        String identifierValue = identifier.getValue();
        int actualQuantityValue = inventoryItem.getQuantity().getValue();
        return new ResponseModel(identifierValue, actualQuantityValue);
    }

}
