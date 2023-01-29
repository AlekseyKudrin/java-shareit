package ru.practicum.shareit.exceptionHandler.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class ValidationFieldsException extends RuntimeException {
    public ValidationFieldsException(String message) {
        super(message);
    }
}
