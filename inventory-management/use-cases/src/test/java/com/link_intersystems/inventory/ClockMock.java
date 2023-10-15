package com.link_intersystems.inventory;

import java.time.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class ClockMock extends Clock {

    private ZoneId zoneId;

    public Queue<LocalDateTime> states = new LinkedList<>();

    public ClockMock() {
        this(ZoneOffset.UTC);
    }

    public ClockMock(ZoneId zone) {
        this.zoneId = Objects.requireNonNull(zone);
    }

    public void add(LocalDateTime... localDateTime) {
        states.addAll(Arrays.asList(localDateTime));
    }


    @Override
    public ZoneId getZone() {
        return zoneId;
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return new ClockMock(zone);
    }

    @Override
    public Instant instant() {
        LocalDateTime nextState = states.poll();
        if (nextState == null) {
            throw new IllegalStateException("No more clock states");
        }
        return nextState.toInstant(zoneId.getRules().getOffset(nextState));
    }
}
