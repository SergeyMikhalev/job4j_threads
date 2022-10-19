package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Runnable> queue = new SimpleBlockingQueue<>(10);
        EnchancedThread thread = new EnchancedThread(queue);
        //thread.run();
        queue.offer(() -> System.out.println("great!"));
        System.out.println(thread.isWaiting());
        //thread.notify();
        thread.wakeUp();
        Thread.sleep(1000);
        System.out.println(thread.isWaiting());
        thread.shutDown();
    }

}
