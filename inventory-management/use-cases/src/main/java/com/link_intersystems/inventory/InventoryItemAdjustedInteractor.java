package com.link_intersystems.inventory;

import static java.util.Objects.*;

public class InventoryItemAdjustedInteractor implements InventoryItemAdjustedUseCase {

    private InventoryItemAdjustedRepository repository;

    public InventoryItemAdjustedInteractor(InventoryItemAdjustedRepository repository) {
        this.repository = requireNonNull(repository);
    }

    @Override
    public ResponseModel adjustItems(RequestModel requestModel) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(requestModel.identifier());
        AdjustItemEvent adjustItemEvent = AdjustItemEvent.create(identifier, requestModel.quantityDiff());

        repository.persist(adjustItemEvent);

        InventoryItem inventoryItem = repository.findById(identifier);

        return new ResponseModel(inventoryItem.getIdentifier().getValue(), inventoryItem.getQuantity().getValue());
    }
}
