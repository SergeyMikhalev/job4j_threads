package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ConsoleProgress());
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }

    @Override
    public void run() {
        Character[] process = {'\\', '|', '/'};
        int i = 0;
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            index = i % 3;
            System.out.print("\r Loading... " + process[index]);
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.print("\r Loaded");
                Thread.currentThread().interrupt();
            }
        }

    }

}
