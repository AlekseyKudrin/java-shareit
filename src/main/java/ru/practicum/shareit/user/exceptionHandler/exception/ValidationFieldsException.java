package ru.practicum.shareit.user.exceptionHandler.exception;

public class ValidationFieldsException extends RuntimeException{
    public ValidationFieldsException(String message) {
        super(message);
    }
}
