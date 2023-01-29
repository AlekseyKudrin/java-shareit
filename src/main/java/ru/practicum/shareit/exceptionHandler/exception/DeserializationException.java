package ru.practicum.shareit.exceptionHandler.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class DeserializationException extends MethodArgumentNotValidException {
    public DeserializationException(String message) {
        super(null, null);
    }
}
