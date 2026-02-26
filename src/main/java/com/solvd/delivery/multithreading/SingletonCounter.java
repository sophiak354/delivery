package com.solvd.delivery.multithreading;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

public final class SingletonCounter {
    @Getter
    private static final SingletonCounter instance = new SingletonCounter();
    private final AtomicInteger counter = new AtomicInteger(0);

    private SingletonCounter() {

    }

    public int incrementAndGet() {
        return counter.incrementAndGet();
    }

    public int get() {
        return counter.get();
    }

    public void reset() {
        counter.set(0);
    }
}
