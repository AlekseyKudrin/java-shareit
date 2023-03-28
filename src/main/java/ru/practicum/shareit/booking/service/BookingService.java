package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto create(long userId, BookingDto bookingDto);

    BookingDto status(long userId, long bookingId, Boolean isApproved);

    BookingDto getBookingById(Long userId, Long bookingId);

    List<BookingDto> getAllBookingsUser(long userId, String bookingState);

    List<BookingDto> getAllBookingsOwner(long ownerId, String bookingState);

}
