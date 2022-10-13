package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public final class ReadFile {
    private final File file;

    public ReadFile(File file) {
        this.file = file;
    }

    public synchronized String getContent() throws IOException {
        return getFilteredContent(i -> true);
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getFilteredContent(i -> i < 0x80);
    }

    private String getFilteredContent(Predicate<Integer> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        }
        return output.toString();
    }

}
