package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ReadFile {
    private final File file;

    public ReadFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return getFilteredContent((Integer i) -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getFilteredContent((Integer i) -> i < 0x80);
    }

    private String getFilteredContent(Predicate<Integer> filter) throws IOException {
        String output = "";
        try (InputStream i = new FileInputStream(file)) {
            int data;
            while ((data = i.read()) > 0) {
                if (filter.test(data)) {
                    output += (char) data;
                }
            }
        }
        return output;
    }

}
