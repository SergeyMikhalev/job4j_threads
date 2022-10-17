package ru.job4j;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class SimpleBlockingQueueTest {

    @Test
    public void someTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        Thread producer = new Thread(() -> {
            IntStream.range(0, 7).forEach(queue::offer);
        });

        Thread consumer = new Thread(() -> {
            IntStream.range(0, 7).forEach(i -> list.add(queue.poll()));
        });

        consumer.start();
        Thread.sleep(1000);
        producer.start();
        producer.join();
        consumer.join();
        assertIterableEquals(list, List.of(0, 1, 2, 3, 4, 5, 6));
    }
}
