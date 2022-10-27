package ru.job4j.pool;

import java.util.ArrayList;
import java.util.List;
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
            result[i] = oneRowColSum(matrix, i);
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        List<CompletableFuture<Sums>> tasks = new ArrayList<>(matrix.length);
        for (int i = 0; i < matrix.length; i++) {
            tasks.add(asyncOneRowColSum(matrix, i));
        }
        Sums[] result = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            result[i] = tasks.get(i).get();
        }
        return result;
    }

    public static Sums oneRowColSum(int[][] matrix, int num) {
        int colSum = 0;
        int rowSum = 0;
        for (int i = 0; i < matrix.length; i++) {
            rowSum += matrix[num][i];
            colSum += matrix[i][num];
        }
        return new Sums(rowSum, colSum);
    }

    public static CompletableFuture<Sums> asyncOneRowColSum(int[][] matrix, int num) {
        return CompletableFuture.supplyAsync(
                () -> {
                    return oneRowColSum(matrix, num);
                }
        );
    }
}
