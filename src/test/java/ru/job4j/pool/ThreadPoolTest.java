package ru.job4j.pool;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPoolTest {

    @Test
    public void simpleTest() throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        pool.work(() -> {
            IntStream.range(0, 5).forEach(list1::add);
        });
        pool.work(() -> {
            IntStream.range(1, 7).forEach(list2::add);
        });
        Thread.sleep(100);
        pool.shutdown();
        System.out.println("Shut down");
        Assertions.assertIterableEquals(List.of(0, 1, 2, 3, 4), list1);
        Assertions.assertIterableEquals(List.of(1, 2, 3, 4, 5, 6), list2);
    }

}
