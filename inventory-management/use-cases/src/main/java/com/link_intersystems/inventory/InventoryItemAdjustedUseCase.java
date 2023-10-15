package com.link_intersystems.inventory;

public interface InventoryItemAdjustedUseCase {

    public static record RequestModel(String identifier, int quantityDiff) {
    }

    public static record ResponseModel(String identifier, int actualQuantity) {
    }

    public ResponseModel adjustItems(RequestModel requestModel);
}

