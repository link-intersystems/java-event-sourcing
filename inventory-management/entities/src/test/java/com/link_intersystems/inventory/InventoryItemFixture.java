package com.link_intersystems.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryItemFixture {

    public List<InventoryItemEvent> getEvents6InStock(String identifierValue) {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier(identifierValue);

        List<InventoryItemEvent> events = new ArrayList<>();

        events.add(AdjustItemEvent.create(identifier, 4));
        events.add(new ReceiveItemEvent(identifier, new Quantity(10)));
        events.add(new ShipItemEvent(identifier, new Quantity(8)));

        return events;
    }
}
