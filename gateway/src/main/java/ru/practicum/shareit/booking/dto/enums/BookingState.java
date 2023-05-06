package ru.practicum.shareit.booking.dto.enums;

import java.util.Optional;

public enum BookingState {
    // Все
    ALL,
    // Текущие
    CURRENT,
    // Будущие
    FUTURE,
    // Завершенные
    PAST,
    // Отклоненные
    REJECTED,
    // Ожидающие подтверждения
    WAITING;

    public static Optional<ru.practicum.shareit.booking.dto.enums.BookingState> from(String stringState) {
        for (ru.practicum.shareit.booking.dto.enums.BookingState state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
