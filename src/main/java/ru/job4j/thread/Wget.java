package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class Wget implements Runnable {
    private static final String EXAMPLE =
            "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";

    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            URL targetURL = new URL(url);
            String filename = "tmp_" + Path.of(targetURL.getFile()).getFileName().toString();

            System.out.println(filename);
            try (BufferedInputStream in = new BufferedInputStream(targetURL.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(filename)) {


                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                long pastMills = System.currentTimeMillis();
                long curMills;
                long delta;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                    curMills = System.currentTimeMillis();
                    delta = curMills - pastMills;
                    pastMills = curMills;
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        /*String url = args[0];
        int speed = Integer.parseInt(args[1]);*/
        Thread wget = new Thread(new Wget(EXAMPLE, 10));
        wget.start();
        wget.join();
    }
}