package ru.job4j.concurrent;

public class Wget {
    public static void main(String... args) {
        new Thread(() -> {
            for (int i = 0; i < 101; i++) {
                System.out.print("\rLoading : " + i + "%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
