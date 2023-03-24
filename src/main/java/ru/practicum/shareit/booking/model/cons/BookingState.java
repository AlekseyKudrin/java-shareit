package ru.practicum.shareit.booking.model.cons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/* class not used доработать сервис*/
@Getter
@RequiredArgsConstructor
enum BookingState {
    ALL("ALL"),
    CURRENT("CURRENT"),
    PAST("PAST"),
    FUTURE("FUTURE"),
    WAITING("WAITING"),
    REJECTED("REJECTED");
    private final String state;
}