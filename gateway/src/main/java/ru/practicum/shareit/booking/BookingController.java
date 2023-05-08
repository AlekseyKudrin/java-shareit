package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.enums.BookingState;
import ru.practicum.shareit.exceptionHandler.exception.ValidationTimeException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Validated(BookingDto.Create.class)
            @RequestBody BookingDto bookingDto
    ) {
        if (bookingDto.getEnd().isBefore(bookingDto.getStart())) {
            throw new ValidationTimeException("The time is not correct");
        }
        log.info("Creating booking {}, userId={}", bookingDto, userId);
        return bookingClient.create(userId, bookingDto);
    }

    @PatchMapping(path = "/{bookingId}")
    public ResponseEntity<Object> status(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long bookingId,
            @RequestParam(value = "approved") boolean isApproved
    ) {
        return bookingClient.status(userId, bookingId, isApproved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                             @PathVariable Long bookingId
    ) {
        log.info("Get booking {}, userId={}", bookingId, userId);
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBookingsUser(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @RequestParam(name = "state", defaultValue = "all") String bookingState,
                                                     @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                     @Positive @RequestParam(name = "size", defaultValue = "10") Integer size
    ) throws IllegalArgumentException {
        BookingState state = BookingState.from(bookingState)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + bookingState));
        log.info("Get booking with state {}, userId={}, from={}, size={}", state, userId, from, size);
        return bookingClient.getBookings(userId, state, from, size);
    }


    @GetMapping(path = "/owner")
    public ResponseEntity<Object> getAllBookingsOwner(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "20") int size
    ) {
        BookingState state = BookingState.from(bookingState)
                .orElseThrow(() -> new IllegalArgumentException("Unknown state: " + bookingState));
        return bookingClient.getAllBookingsOwner(userId, state, from, size);
    }
}
