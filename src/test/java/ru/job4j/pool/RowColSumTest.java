package ru.job4j.pool;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.ExecutionException;

import static ru.job4j.pool.RowColSum.asyncSum;
import static ru.job4j.pool.RowColSum.sum;

public class RowColSumTest {

    @Test
    public void whenSyncTrivial() {
        int[][] matrix = {{1, 1, 1, 1}, {1, 2, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        RowColSum.Sums four = new RowColSum.Sums(4, 4);
        RowColSum.Sums five = new RowColSum.Sums(5, 5);
        RowColSum.Sums[] sums = sum(matrix);
        Assertions.assertEquals(sums[0], four);
        Assertions.assertEquals(sums[1], five);
        Assertions.assertEquals(sums[2], four);
        Assertions.assertEquals(sums[3], four);
    }

    @Test
    public void whenAsyncTrivial() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 1, 1, 1}, {1, 2, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        RowColSum.Sums four = new RowColSum.Sums(4, 4);
        RowColSum.Sums five = new RowColSum.Sums(5, 5);
        RowColSum.Sums[] sums = asyncSum(matrix);
        Assertions.assertEquals(sums[0], four);
        Assertions.assertEquals(sums[1], five);
        Assertions.assertEquals(sums[2], four);
        Assertions.assertEquals(sums[3], four);
    }

    @Test
    public void whenSyncOneRowAndCol() {
        int[][] matrix = {{21}};
        RowColSum.Sums expected = new RowColSum.Sums(21, 21);
        RowColSum.Sums[] sums = sum(matrix);
        Assertions.assertEquals(sums[0], expected);
    }

    @Test
    public void whenAsyncOneRowAndCol() throws ExecutionException, InterruptedException {
        int[][] matrix = {{0}};
        RowColSum.Sums expected = new RowColSum.Sums(0, 0);
        RowColSum.Sums[] sums = asyncSum(matrix);
        Assertions.assertEquals(sums[0], expected);
    }
}
