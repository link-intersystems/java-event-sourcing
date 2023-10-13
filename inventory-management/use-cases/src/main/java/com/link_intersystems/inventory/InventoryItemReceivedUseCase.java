package com.link_intersystems.inventory;

import static java.util.Objects.*;

public interface InventoryItemReceivedUseCase {

    public ResponseModel itemsReceived(RequestModel requestModel);

    public static record RequestModel(String itemIdentifier, int receivedQuantity) {

        public RequestModel {
            requireNonNull(itemIdentifier);
        }
    }

    public static record ResponseModel(String itemIdentifier, int actualQuantity) {

        public ResponseModel {
            requireNonNull(itemIdentifier);
        }
    }
}
