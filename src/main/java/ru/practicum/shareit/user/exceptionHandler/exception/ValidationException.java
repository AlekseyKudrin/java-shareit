package ru.practicum.shareit.user.exceptionHandler.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
