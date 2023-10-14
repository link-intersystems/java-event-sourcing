package com.link_intersystems.inventory;

import java.util.Objects;

import static java.util.Objects.*;

public class InventoryItem {

    private InventoryItemIdentifier identifier;
    private Quantity quantity = new Quantity(0);

    public InventoryItem(InventoryItemIdentifier identifier) {
        this.identifier = requireNonNull(identifier);
    }

    public InventoryItemIdentifier getIdentifier() {
        return identifier;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    void setQuantity(Quantity quantity) {
        this.quantity = requireNonNull(quantity);
    }

    static abstract class QuantityEvent extends InventoryItemEvent {

        private Quantity quantityDiff;

        public QuantityEvent(InventoryItemIdentifier identifier, Quantity quantityDiff) {
            super(identifier);
            this.quantityDiff = requireNonNull(quantityDiff);
        }

        @Override
        protected void doApply(InventoryItem inventoryItem) {
            if (!Objects.equals(this.getIdentifier(), inventoryItem.getIdentifier())) {
                throw new IllegalArgumentException(this + " can not be applied to " + inventoryItem + ", because of a different identifier.");
            }

            Quantity newQuantity = getNewQuantity(inventoryItem, quantityDiff);
            inventoryItem.setQuantity(newQuantity);
        }

        protected abstract Quantity getNewQuantity(InventoryItem inventoryItem, Quantity quantityDiff);

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuantityEvent quantityEvent = (QuantityEvent) o;
            if (!Objects.equals(quantityDiff, quantityEvent.quantityDiff)) {
                return false;
            }

            return super.equals(o);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), quantityDiff);
        }
    }

}
