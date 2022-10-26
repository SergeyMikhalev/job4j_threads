package ru.job4j.pool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums() {
            this(0, 0);
        }

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums(colSum(matrix, i), rowSum(matrix, i));
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        ArrayList<CompletableFuture<Integer>> tasks = new ArrayList<>(2 * matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            tasks.add(asyncColSum(matrix, i));
            tasks.add(asyncRowSum(matrix, i));
        }

        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums(
                    tasks.get(2 * i).get(),
                    tasks.get(2 * i + 1).get()
            );
        }

        return result;
    }

    public static int colSum(int[][] matrix, int colNum) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[i][colNum];
        }
        return result;
    }

    public static int rowSum(int[][] matrix, int rowNum) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[rowNum][i];
        }
        return result;
    }

    public static CompletableFuture<Integer> asyncRowSum(int[][] matrix, int rowNum) {
        return CompletableFuture.supplyAsync(
                () -> {
                    return rowSum(matrix, rowNum);
                }
        );
    }

    public static CompletableFuture<Integer> asyncColSum(int[][] matrix, int colNum) {
        return CompletableFuture.supplyAsync(
                () -> {
                    return colSum(matrix, colNum);
                }
        );
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[][] matrix = {{1, 1, 1, 1}, {1, 2, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}};
        System.out.println("Sync call");
        Arrays.stream(sum(matrix)).forEach(System.out::println);
        System.out.println("Async call");
        Arrays.stream(asyncSum(matrix)).forEach(System.out::println);

    }
}
