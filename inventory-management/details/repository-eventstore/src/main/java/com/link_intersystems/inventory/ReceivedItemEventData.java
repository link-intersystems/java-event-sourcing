package com.link_intersystems.inventory;

import java.time.LocalDateTime;

class ReceivedItemEventData {

    private String identifier;
    private LocalDateTime appliedTime;
    private int quantity;

    public ReceivedItemEventData() {
    }

    public ReceivedItemEventData(ReceiveItemEvent itemEvent) {
        this.appliedTime = itemEvent.getAppliedTime();
        this.identifier = itemEvent.getIdentifier().getValue();
        this.quantity = itemEvent.getQuantity().getValue();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDateTime getAppliedTime() {
        return appliedTime;
    }

    public void setAppliedTime(LocalDateTime appliedTime) {
        this.appliedTime = appliedTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
