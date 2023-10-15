package com.link_intersystems.inventory;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JsonSerdesTest {

    private JsonSerdes serdes;

    @BeforeEach
    void setUp() {
        serdes = new JsonSerdes();
    }


    @Test
    void serdes() {
        Person person = new Person();
        person.setFirstname("René");
        person.setLastname("Link");

        byte[] serialized = serdes.serialize(person);
        Person deserialized = serdes.deserialize(Person.class, serialized);

        assertEquals(person, deserialized);
    }

    @Test
    void serializationException() {
        JSONObject jsonObject = new JSONObject();
        IOException ioException = new IOException();
        OutputStream out = mock(OutputStream.class, invocation -> {
            throw ioException;
        });

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> serdes.write(jsonObject, out));
        assertEquals(ioException, runtimeException.getCause());
    }

}
