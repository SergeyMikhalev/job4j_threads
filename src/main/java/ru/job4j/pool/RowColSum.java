package ru.job4j.pool;

public class RowColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

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
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        for (int i =0;i<matrix.length;i++){
            result[i].setColSum(ColSum(matrix,i));
            result[i].setRowSum(RowSum(matrix,i));
        }
        return result;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        return null;
    }

    public static int ColSum(int[][] matrix, int colNum) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[i][colNum];
        }
        return result;
    }

    public static int RowSum(int[][] matrix, int rowNum) {
        int result = 0;
        for (int i = 0; i < matrix.length; i++) {
            result += matrix[rowNum][i];
        }
        return result;
    }
}
