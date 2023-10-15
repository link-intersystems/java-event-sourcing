package com.link_intersystems.inventory;

import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.RecordedEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class InventoryItemEventMapper {

    private JsonSerdes serdes = new JsonSerdes();

    private Map<Class<?>, Class<?>> itemEventTypeMapping = new HashMap<>();

    InventoryItemEventMapper() {
        itemEventTypeMapping.put(ReceiveItemEvent.class, ReceivedItemEventData.class);
        itemEventTypeMapping.put(AdjustItemEvent.class, AdjustedItemEventData.class);
        itemEventTypeMapping.put(ShipItemEvent.class, ShipItemEventData.class);
    }

    public InventoryItemEvent toDomain(RecordedEvent recordedEvent) {
        EventData eventData = EventData.builderAsBinary(recordedEvent.getEventType(), recordedEvent.getEventData()).build();
        return toDomain(eventData);
    }

    public InventoryItemEvent toDomain(EventData eventData) {
        String eventType = eventData.getEventType();
        try {
            Class<ItemEventData> eventClass = (Class<ItemEventData>) Class.forName(eventType);
            ItemEventData itemEventData = serdes.deserialize(eventClass, eventData.getEventData());
            return itemEventData.toDomain();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public EventData toEventData(InventoryItemEvent event) {
        Class<?> eventDataClass = itemEventTypeMapping.get(event.getClass());
        Object obj = newInstance(eventDataClass, event);
        byte[] serialized = serdes.serialize(obj);
        String eventType = eventDataClass.getName();
        return EventData.builderAsBinary(eventType, serialized).build();
    }

    Object newInstance(Class<?> eventDataClass, InventoryItemEvent event) {
        try {
            Constructor<?> declaredConstructor = eventDataClass.getDeclaredConstructor(event.getClass());
            return declaredConstructor.newInstance(event);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
