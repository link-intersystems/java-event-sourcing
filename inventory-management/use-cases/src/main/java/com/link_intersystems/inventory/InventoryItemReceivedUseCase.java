package com.link_intersystems.inventory;

public interface InventoryItemReceivedUseCase {

    public ResponseModel itemsReceived(RequestModel requestModel);

    public static record RequestModel(String itemIdentifier, int receivedQuantity) {
    }

    public static record ResponseModel(String itemIdentifier, int actualQuantity) {
    }
}
