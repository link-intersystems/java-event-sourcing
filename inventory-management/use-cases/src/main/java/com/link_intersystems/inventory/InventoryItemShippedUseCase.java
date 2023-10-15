package com.link_intersystems.inventory;

public interface InventoryItemShippedUseCase {

    public static record RequestModel(String identifier, int shippedQuantity) {
    }

    public static record ResponseModel(String identifier, int actualQuantity) {
    }

    public ResponseModel shipItems(RequestModel requestModel);
}

