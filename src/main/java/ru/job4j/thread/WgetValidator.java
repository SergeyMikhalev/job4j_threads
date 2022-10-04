package ru.job4j.thread;

public class WgetValidator {
    private String url = "not valid url";
    private int speed = -1;

    public WgetValidator() {
    }

    public boolean validate(String... args) {
        if (args.length < 2) {
            return false;
        }

        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (speed < 1) {
            return false;
        }

        url = args[0];
        if (url.isEmpty()) {
            return false;
        }

        return true;
    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }
}
