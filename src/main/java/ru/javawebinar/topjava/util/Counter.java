package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    private Counter() {
    }
}
