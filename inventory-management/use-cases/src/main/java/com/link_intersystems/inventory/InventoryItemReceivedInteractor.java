package com.link_intersystems.inventory;

import static java.util.Objects.*;

public class InventoryItemReceivedInteractor implements InventoryItemReceivedUseCase {

    private InventoryItemReceivedRepository repository;

    public InventoryItemReceivedInteractor(InventoryItemReceivedRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public ResponseModel itemsReceived(RequestModel requestModel) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(requestModel.getItemIdentifier());
        Quantity receivedQuantity = new Quantity(requestModel.getReceivedQuantity());

        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(identifier, receivedQuantity);
        repository.persist(receiveItemEvent);

        InventoryItem inventoryItem = repository.findById(identifier);

        ResponseModel responseModel = new ResponseModel();
        responseModel.setItemIdentifier(identifier.getValue());
        responseModel.setActualQuantity(inventoryItem.getQuantity().getValue());

        return responseModel;
    }

}
