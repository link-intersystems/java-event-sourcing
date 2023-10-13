package com.link_intersystems.inventory;

import java.util.Objects;

public class InventoryItemIdentifier {

    private String value;

    public InventoryItemIdentifier(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Identifier must not be blank");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryItemIdentifier that = (InventoryItemIdentifier) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
