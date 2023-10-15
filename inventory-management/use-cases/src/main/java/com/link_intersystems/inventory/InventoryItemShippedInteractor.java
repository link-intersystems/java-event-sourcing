package com.link_intersystems.inventory;

import static java.util.Objects.*;

public class InventoryItemShippedInteractor implements InventoryItemShippedUseCase {

    private InventoryItemShippedRepository repository;

    public InventoryItemShippedInteractor(InventoryItemShippedRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public ResponseModel shipItems(RequestModel requestModel) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(requestModel.identifier());
        ShipItemEvent shipItemEvent = new ShipItemEvent(identifier, new Quantity(requestModel.shippedQuantity()));

        repository.persist(shipItemEvent);

        InventoryItem inventoryItem = repository.findById(identifier);

        return new ResponseModel(inventoryItem.getIdentifier().getValue(), inventoryItem.getQuantity().getValue());
    }
}
