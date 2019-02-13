package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static AtomicInteger count = new AtomicInteger(0);

    private Counter() {
    }

    public static int getIncrement() {
        return count.incrementAndGet();
    }
}
