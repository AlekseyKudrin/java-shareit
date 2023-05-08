package ru.practicum.shareit.exceptionHandler.exception;

public class ValidationException extends IllegalArgumentException {
    public ValidationException(String message) {
        super(message);
    }
}
