package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

public class SingleThreadExecutor {

    private final SimpleBlockingQueue<Runnable> tasks;
    private final Thread thread;

    public SingleThreadExecutor(SimpleBlockingQueue<Runnable> tasks) {
        this.tasks = tasks;
        thread = new Thread(this::process);
        thread.start();
    }

    public synchronized void process() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while ((!Thread.currentThread().isInterrupted()) && tasks.isEmpty()) {
                    wait();
                }
                tasks.poll().run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized boolean isWaiting() {
        return (Thread.State.WAITING == thread.getState());
    }


    public synchronized void wakeUp() {
        notifyAll();
    }

    public void shutDown() {
        //System.out.println(Thread.currentThread().getName() + "Going down!");
        thread.interrupt();
    }
}