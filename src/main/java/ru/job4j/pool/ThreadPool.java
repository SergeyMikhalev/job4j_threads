package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(this::process);
            threads.add(thread);
            thread.start();
        }
    }

    private void process() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                while ((!Thread.currentThread().isInterrupted()) && tasks.isEmpty()) {
                   Thread.currentThread().wait();
                }
                tasks.poll().run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        threads.forEach(thread -> {
            if (Thread.State.WAITING == Thread.currentThread().getState()) {
                thread.notify();
            }
        });
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}