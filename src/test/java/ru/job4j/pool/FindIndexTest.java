package ru.job4j.pool;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.ForkJoinPool;

public class FindIndexTest {

    @Test
    public void whenSmallString() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        String[] strings = {"Klara", "Anna", "Petr"};
        int index = forkJoinPool
                .invoke(new FindIndex<>(strings, 0, strings.length, "Anna"));
        Assertions.assertEquals(1, index);
    }

    @Test
    public void whenBigInteger() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 22};
        int index = forkJoinPool
                .invoke(new FindIndex<>(ints, 0, ints.length, 17));
        Assertions.assertEquals(16, index);
    }

    @Test
    public void whenCantFindBig() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 22};
        int index = forkJoinPool
                .invoke(new FindIndex<>(ints, 0, ints.length, 99));
        Assertions.assertEquals(-1, index);
    }

    @Test
    public void whenCantFindSmall() {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        String[] strings = {"Klara", "Anna", "Petr"};
        int index = forkJoinPool
                .invoke(new FindIndex<>(strings, 0, strings.length, "Liza"));
        Assertions.assertEquals(-1, index);
    }

}

