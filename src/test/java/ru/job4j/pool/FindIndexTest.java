package ru.job4j.pool;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FindIndexTest {

    @Test
    public void whenSmallString() {
        String[] strings = {"Klara", "Anna", "Petr"};
        int index = FindIndex.find(strings, "Anna");
        Assertions.assertEquals(1, index);
    }

    @Test
    public void whenBigInteger() {
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 22};
        int index = FindIndex.find(ints, 17);
        Assertions.assertEquals(16, index);
    }

    @Test
    public void whenCantFindBig() {
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 22};
        int index = FindIndex.find(ints, 99);
        Assertions.assertEquals(-1, index);
    }

    @Test
    public void whenCantFindSmall() {
        String[] strings = {"Klara", "Anna", "Petr"};
        int index = FindIndex.find(strings, "Liza");
        Assertions.assertEquals(-1, index);
    }

    @Test
    public void whenFindLast() {
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 22};
        int index = FindIndex.find(ints, 22);
        Assertions.assertEquals(20, index);
    }

}

