package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
            @RequestParam(value = "state", defaultValue = "ALL", required = false) String bookingState,
            @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
            @Positive @RequestParam(defaultValue = "20", required = false) int size
    ) {
        return bookingService.getAllBookingsUser(userId, bookingState, from, size);
    }


    @GetMapping(path = "/owner")
    public List<BookingDto> getAllBookingsOwner(
            @RequestHeader("X-Sharer-User-Id") long ownerId,
            @RequestParam(value = "state", defaultValue = "ALL", required = false) String bookingState,
            @PositiveOrZero @RequestParam(defaultValue = "0", required = false) int from,
            @Positive @RequestParam(defaultValue = "20", required = false) int size
    ) {
        return bookingService.getAllBookingsOwner(ownerId, bookingState, from, size);
    }
}
