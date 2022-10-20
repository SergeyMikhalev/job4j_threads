package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count;

    public CASCount(int initial) {
        count = new AtomicReference<>(initial);
    }

    public void increment() {
        Integer temp;
        do {
            temp = count.get();
        } while (!count.compareAndSet(temp, temp + 1));
    }

    public int get() {
        return count.get();
    }

}
