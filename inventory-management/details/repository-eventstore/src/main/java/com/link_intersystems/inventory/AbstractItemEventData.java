package com.link_intersystems.inventory;

public class AbstractItemEventData {
    private String identifier;
    private String appliedTime;
    private int quantity;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAppliedTime() {
        return appliedTime;
    }

    public void setAppliedTime(String appliedTime) {
        this.appliedTime = appliedTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
