package com.link_intersystems.inventory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.util.Objects.*;

public class FutureHandler<V> {

    private Future<V> future;

    public FutureHandler(Future<V> future) {
        this.future = requireNonNull(future);
    }

    public <R> R mapWhenPresent(Function<V, R> mappingFunction) {
        V value = waitForResult();
        return mappingFunction.apply(value);
    }

    public V waitForResult() {
        try {
            return this.future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
