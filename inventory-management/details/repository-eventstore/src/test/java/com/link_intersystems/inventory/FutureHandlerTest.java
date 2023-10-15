package com.link_intersystems.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FutureHandlerTest {

    private FutureHandler<String> futureHandler;
    private Future<String> future;

    @BeforeEach
    void setUp() {
        future = mock(Future.class);
        futureHandler = new FutureHandler<>(future);
    }

    @Test
    void mapWhenPresent() throws ExecutionException, InterruptedException {
        when(future.get()).thenReturn("1");

        Integer value = futureHandler.mapWhenPresent(Integer::valueOf);
        assertEquals(Integer.valueOf(1), value);
    }

    @Test
    void mapWhenPresentExecutionException() throws ExecutionException, InterruptedException {
        ExecutionException executionException = new ExecutionException(new RuntimeException());
        when(future.get()).thenThrow(executionException);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> futureHandler.mapWhenPresent(Integer::valueOf));
        assertEquals(executionException, runtimeException.getCause());
    }


    @Test
    void mapWhenPresentInterruptedException() throws ExecutionException, InterruptedException {
        InterruptedException interruptedException = new InterruptedException();
        when(future.get()).thenThrow(interruptedException);

        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> futureHandler.mapWhenPresent(Integer::valueOf));
        assertEquals(interruptedException, runtimeException.getCause());
    }
}