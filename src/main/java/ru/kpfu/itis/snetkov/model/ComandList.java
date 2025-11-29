package ru.kpfu.itis.snetkov.model;

public enum ComandList {
    LIST("list"),
    WEATHER("weather"),
    EXCHANGE("exchange"),
    QUIT("quit");

    public final String name;
    ComandList(String name) {
        this.name = name;
    }

    public static ComandList fromString(String text) {
        for (ComandList comand : ComandList.values()) {
            if (text.toLowerCase().equals(comand.name))return comand;
        }
        throw new IllegalArgumentException("Некорректная команда");
    }
}
