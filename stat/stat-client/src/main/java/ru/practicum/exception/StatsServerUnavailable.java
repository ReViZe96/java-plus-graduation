package ru.practicum.exception;

import lombok.Getter;

public class StatsServerUnavailable  extends RuntimeException {
    @Getter
    String description;

    public StatsServerUnavailable(String description) {
        this.description = description;
    }
}
