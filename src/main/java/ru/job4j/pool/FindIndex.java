package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindIndex<T> extends RecursiveTask<Integer> {

    public static final int LINEAR_ALG_SIZE = 10;
    private final T[] objects;
    private final int start;
    private final int end;
    private final T target;

    public FindIndex(T[] objects, int start, int end, T target) {
        this.objects = objects;
        this.start = start;
        this.end = end;
        this.target = target;
    }

    @Override
    protected Integer compute() {
        int result;
        if ((end - start) > LINEAR_ALG_SIZE) {
            result = bigSearch();
        } else {
            result = smallSearch();
        }
        return result;
    }

    private Integer smallSearch() {
        int result = -1;
        for (int i = start; i < end; i++) {
            if (target.equals(objects[i])) {
                result = i;
                break;
            }
        }
        return result;
    }

    private Integer bigSearch() {
        int mid = start + (end - start) / 2;
        FindIndex<T> leftFind = new FindIndex<>(objects, start, mid, target);
        FindIndex<T> rightFind = new FindIndex<>(objects, mid, end, target);
        leftFind.fork();
        rightFind.fork();
        return Math.max(leftFind.join(), rightFind.join());
    }

    public static void main(String[] args) {
        Integer[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 22};
        Integer[] ints2 = {1, 2, 3, 4, 5, 6};
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int result = forkJoinPool.invoke(new FindIndex<>(ints, 0, ints.length, 22));
        System.out.println(result);
    }
}
