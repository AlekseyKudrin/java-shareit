package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingDto add(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                          @RequestBody BookingDtoWithId booking) {
        return bookingService.add(userId, booking);
    }

    @PatchMapping(path = "/{bookingId}")
    public BookingDto changeStatus(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                   @PathVariable long bookingId,
                                   @RequestParam(value = "approved") boolean isApproved) {
        return bookingService.changeStatus(userId, bookingId, isApproved);
    }

    @GetMapping(path = "/{bookingId}")
    public BookingDto getByUserIdAndBookingId(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                              @PathVariable long bookingId) {
        return bookingService.getByUserIdAndBookingId(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> getAllById(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return bookingService.findAllByUserId(userId);
    }

    @GetMapping(path = "/owner")
    public List<BookingDto> getAllByOwner(@RequestHeader(value = "X-Sharer-User-Id") long userId) {
        return bookingService.findAllByOwnerId(userId);
    }

    @GetMapping(params = "state")
    public List<BookingDto> getByState(@RequestHeader(value = "X-Sharer-User-Id") long userId,
                                       @RequestParam String state) {
        return bookingService.getUserIdAndByState(userId, state);
    }

    @GetMapping(path = "/owner", params = "state")
    public List<BookingDto> getByOwnerState(@RequestHeader(value = "X-Sharer-User-Id") long ownerId,
                                            @RequestParam String state) {
        return bookingService.getOwnerIdAndByState(ownerId, state);
    }
}
