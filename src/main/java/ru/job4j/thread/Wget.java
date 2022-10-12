package ru.job4j.thread;

import ru.job4j.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class Wget implements Runnable {
    public static final String EXAMPLE =
            "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
    public static final String EXAMPLE_10MB = "https://proof.ovh.net/files/10Mb.dat";

    private static final int MB = 1024 * 1024;
    private static final int ONE_SEC_IN_MILLS = 1000;

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
            spedLimitedCopy(targetURL, filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void spedLimitedCopy(URL targetURL, String filename) {
        try (BufferedInputStream in = new BufferedInputStream(targetURL.openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long bytesReadAccum = 0;
            boolean continueReading = true;
            long startMills = System.currentTimeMillis();
            long delta;
            while (continueReading) {
                bytesRead = in.read(dataBuffer, 0, 1024);
                bytesReadAccum += bytesRead;
                continueReading = (bytesRead != -1);
                if (continueReading) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                if (bytesReadAccum >= (long)speed * MB) {
                    bytesReadAccum = 0;
                    delta = System.currentTimeMillis() - startMills;
                    startMills = System.currentTimeMillis();
                    if (delta < ONE_SEC_IN_MILLS) {
                        Thread.sleep(ONE_SEC_IN_MILLS - delta);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        EncodingUtils.setConsoleEncodingUTF8();
        WgetValidator validator = new WgetValidator();
        try {
            validator.validate(args);
            Thread wget = new Thread(new Wget(validator.getUrl(),
                    validator.getSpeed()));
            wget.start();
            wget.join();
        } catch (IllegalArgumentException e) {
            System.out.println("Неверно заданы аргументы программы!");
            System.out.println(e.getMessage());
        }
    }
}