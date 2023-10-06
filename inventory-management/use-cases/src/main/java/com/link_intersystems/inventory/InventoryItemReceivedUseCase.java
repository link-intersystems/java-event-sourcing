package com.link_intersystems.inventory;

public interface InventoryItemReceivedUseCase {

    public static class RequestModel {

        private int receivedQuantity;
        private String itemIdentifier;

        public int getReceivedQuantity() {
            return receivedQuantity;
        }

        public void setReceivedQuantity(int receivedQuantity) {
            this.receivedQuantity = receivedQuantity;
        }

        public String getItemIdentifier() {
            return itemIdentifier;
        }

        public void setItemIdentifier(String itemIdentifier) {
            this.itemIdentifier = itemIdentifier;
        }
    }

    public static class ResponseModel {

        private int actualQuantity;
        private String itemIdentifier;

        public void setActualQuantity(int actualQuantity) {
            this.actualQuantity = actualQuantity;
        }

        public int getActualQuantity() {
            return actualQuantity;
        }

        public void setItemIdentifier(String itemIdentifier) {
            this.itemIdentifier = itemIdentifier;
        }

        public String getItemIdentifier() {
            return itemIdentifier;
        }
    }

    public ResponseModel itemsReceived(RequestModel requestModel);
}
