package com.link_intersystems.inventory;

import com.eventstore.dbclient.EventData;
import com.link_intersystems.junit.ext.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class InventoryItemEventMapperTest {

    private InventoryItemEventMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new InventoryItemEventMapper();
    }


    static Stream<InventoryItemEvent> events() {
        InventoryItemIdentifier identifier = new InventoryItemIdentifier("a");
        Quantity quantity = new Quantity(6);
        ReceiveItemEvent receiveItemEvent = new ReceiveItemEvent(identifier, quantity);
        receiveItemEvent.setAppliedTime(LocalDateTime.now());
        AdjustItemEvent adjustItemEvent = AdjustItemEvent.create(identifier, 5);
        adjustItemEvent.setAppliedTime(LocalDateTime.now());
        return Stream.of(receiveItemEvent, adjustItemEvent);
    }


    @ParameterizedTest
    @MethodSource("events")
    void mapEvent(InventoryItemEvent event) {
        EventData eventData = mapper.toEventData(event);
        InventoryItemEvent fromEventData = mapper.toDomain(eventData);

        assertEquals(event, fromEventData);
    }


    @Test
    void classNotFound() {
        EventData eventData = EventData.builderAsBinary("unknownClass", new byte[0]).build();
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> mapper.toDomain(eventData));

        assertInstanceOf(ClassNotFoundException.class, runtimeException.getCause());
    }

    @Test
    void newInstanceWrongType() {
        ReceiveItemEvent event = new ReceiveItemEvent(new InventoryItemIdentifier("a"), new Quantity(2));

        assertThrows(RuntimeException.class, () -> mapper.newInstance(String.class, event));
    }
}