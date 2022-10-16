package ru.job4j;

public class CountBarrierUsage {
    public static void main(String... args) throws InterruptedException {
        CountBarrier barrier = new CountBarrier(3);

        Thread first = new Thread(() -> {
            System.out.println("First thread start");
            barrier.count();
            barrier.await();
            System.out.println("First thread done");
        });

        Thread second = new Thread(() -> {
            System.out.println("Second thread start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            barrier.count();
            barrier.await();
            System.out.println("Second thread done");
        });

        Thread third = new Thread(() -> {
            System.out.println("Third thread start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            barrier.count();
            barrier.await();
            System.out.println("Third thread done");
        });

        first.start();
        third.start();
        second.start();

        third.join();
        first.join();
        second.join();

        System.out.println("barrier state: count -> " + barrier.getCount());

    }
}
