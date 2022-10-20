package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<SingleThreadExecutor> executors = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            executors.add(new SingleThreadExecutor(tasks));
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        executors.forEach(thread -> {
            if (thread.isWaiting()) {
                thread.wakeUp();
            }
        });
    }

    public void shutdown() {
        executors.forEach(SingleThreadExecutor::shutDown);
    }
}