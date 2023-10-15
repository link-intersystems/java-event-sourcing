package com.link_intersystems.inventory;

import static java.util.Objects.*;

public interface InventoryItemReceivedUseCase {

    public ResponseModel itemsReceived(RequestModel requestModel);

    public static record RequestModel(String identifier, int receivedQuantity) {

        public RequestModel {
            requireNonNull(identifier);
        }
    }

    public static record ResponseModel(String identifier, int actualQuantity) {

        public ResponseModel {
            requireNonNull(identifier);
        }
    }
}
