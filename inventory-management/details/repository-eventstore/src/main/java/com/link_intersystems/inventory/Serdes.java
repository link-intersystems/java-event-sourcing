package com.link_intersystems.inventory;

public interface Serdes {
    byte[] serialize(Object event);

    <T> T deserialize(Class<T> type, byte[] data);
}
