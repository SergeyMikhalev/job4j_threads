package ru.job4j.thread;

public class WgetValidator {
    private String url = "not valid url";
    private int speed = -1;

    public WgetValidator() {
    }

    public void validate(String... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Недостаточное "
                    + "количество параметров для запуска. Требуется 2.");
        }

        try {
            speed = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Параметр ограничения "
                    + "скорости должен быть целым числом!");
        }
        if (speed < 1) {
            throw new IllegalArgumentException("Минимальное ограничение "
                    + "скорости = 1");
        }

        url = args[0];
        if (url.isEmpty()) {
            throw new IllegalArgumentException("Не задан"
                    + "файл для скачивания!");
        }

    }

    public String getUrl() {
        return url;
    }

    public int getSpeed() {
        return speed;
    }
}
