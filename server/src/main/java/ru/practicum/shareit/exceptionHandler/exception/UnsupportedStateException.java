package ru.practicum.shareit.exceptionHandler.exception;

public class UnsupportedStateException extends IllegalArgumentException {
    public UnsupportedStateException(String message) {
        super(message);
    }
}
