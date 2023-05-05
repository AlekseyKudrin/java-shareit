package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;


@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Validated(BookingDto.Create.class) @RequestBody BookingDto bookingDto) {
        return bookingService.create(userId, bookingDto);
    }

    @PatchMapping(path = "/{bookingId}")
    public BookingDto status(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long bookingId,
            @RequestParam(value = "approved") boolean isApproved
    ) {
        return bookingService.status(userId, bookingId, isApproved);
    }

    @GetMapping(path = "/{bookingId}")
    public BookingDto getBookingById(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @PathVariable long bookingId
    ) {
        return bookingService.getBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllBookingsUser(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size
    ) {
        return bookingService.getAllBookingsUser(userId, bookingState, from, size);
    }


    @GetMapping(path = "/owner")
    public List<BookingDto> getAllBookingsOwner(
            @RequestHeader("X-Sharer-User-Id") long ownerId,
            @RequestParam(value = "state", defaultValue = "ALL") String bookingState,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size
    ) {
        return bookingService.getAllBookingsOwner(ownerId, bookingState, from, size);
    }
}
