package com.link_intersystems.inventory;

import static java.util.Objects.*;

public class AdjustItemEvent extends InventoryItem.QuantityEvent {

    private final AdjustOperation adjustOperation;

    protected AdjustItemEvent(InventoryItemIdentifier identifier, AdjustOperation adjustOperation, Quantity quantityDiff) {
        super(identifier, quantityDiff);
        this.adjustOperation = requireNonNull(adjustOperation);
    }

    public static AdjustItemEvent create(InventoryItemIdentifier identifier, int quantity) {
        if (quantity < 0) {
            Quantity decreaseQuantity = new Quantity(Math.abs(quantity));
            return new AdjustItemEvent(identifier, AdjustOperation.DECREASE, decreaseQuantity);
        } else {
            return new AdjustItemEvent(identifier, AdjustOperation.INCREASE, new Quantity(quantity));
        }
    }

    @Override
    protected Quantity getNewQuantity(InventoryItem inventoryItem, Quantity quantityDiff) {
        Quantity oldQuantity = inventoryItem.getQuantity();
        return adjustOperation.apply(oldQuantity, quantityDiff);
    }

    public static enum AdjustOperation {
        INCREASE {
            @Override
            public Quantity apply(Quantity oldQuantity, Quantity quantityDiff) {
                return oldQuantity.add(quantityDiff);
            }
        },
        DECREASE {
            @Override
            public Quantity apply(Quantity oldQuantity, Quantity quantityDiff) {
                return oldQuantity.substract(quantityDiff);
            }
        };

        public abstract Quantity apply(Quantity oldQuantity, Quantity quantityDiff);
    }
}
