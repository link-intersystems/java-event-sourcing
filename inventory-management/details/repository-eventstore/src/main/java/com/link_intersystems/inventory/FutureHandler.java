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
        try {
            V value = this.future.get();
            return mappingFunction.apply(value);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
