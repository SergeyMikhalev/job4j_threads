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

        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r Loading... " + process[i % 3]);
            i++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
