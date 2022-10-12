package ru.job4j.thread;

import ru.job4j.EncodingUtils;

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
    private final int delay;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        this.delay = 1000 / speed;
    }

    @Override
    public void run() {
        try {
            URL targetURL = new URL(url);
            String filename = "tmp_" + Path.of(targetURL.getFile()).getFileName().toString();
            try (BufferedInputStream in = new BufferedInputStream(targetURL.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                boolean continueReading = true;
                long mills;
                long delta;
                while (continueReading) {
                    mills = System.currentTimeMillis();
                    bytesRead = in.read(dataBuffer, 0, 1024);
                    continueReading = (bytesRead != -1);
                    if (continueReading) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                    delta = System.currentTimeMillis() - mills;
                    if (delta < delay) {
                        Thread.sleep(delay - delta);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EncodingUtils.setConsoleEncodingUTF8();
        WgetValidator validator = new WgetValidator();
        if (validator.validate(args)) {
            Thread wget = new Thread(new Wget(validator.getUrl(),
                    validator.getSpeed()));
            wget.start();
            wget.join();
        } else {
            System.out.println("Неверно заданы аргументы программы!");
        }
    }
}