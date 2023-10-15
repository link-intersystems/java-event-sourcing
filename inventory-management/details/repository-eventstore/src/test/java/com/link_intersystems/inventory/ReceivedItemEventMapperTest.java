package com.link_intersystems.inventory;

import com.eventstore.dbclient.RecordedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceivedItemEventMapperTest {

    private ReceivedItemEventMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ReceivedItemEventMapper();
    }

    @Test
    void mapRecordedEvent() throws IOException {
        RecordedEvent recordedEvent = mock(RecordedEvent.class);
        IOException ioException = new IOException();
        when(recordedEvent.getEventDataAs(ReceivedItemEventData.class)).thenThrow(ioException);

        RuntimeException raisedException = assertThrows(RuntimeException.class, () -> mapper.mapRecordedEvent(recordedEvent));

        assertEquals(ioException, raisedException.getCause());
    }
}