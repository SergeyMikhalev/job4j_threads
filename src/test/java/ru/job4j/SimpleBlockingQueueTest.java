package ru.job4j;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class SimpleBlockingQueueTest {

    @Test
    public void someTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 7; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 7; i++) {
                    list.add(queue.poll());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumer.start();
        Thread.sleep(1000);
        producer.start();
        producer.join();
        consumer.join();
        assertIterableEquals(list, List.of(0, 1, 2, 3, 4, 5, 6));
    }
}
