package ru.practicum.shareit.booking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.enums.BookingState;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptionHandler.exception.UnsupportedStateException;
import ru.practicum.shareit.exceptionHandler.exception.ValidationException;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;


    @Override
    public BookingDto create(long userId, BookingDto bookingDto) {
        Booking booking = BookingMapper.toBooking(
                appropriateBookerAndItem(userId, bookingDto)
        );
        booking.setStatus(BookingStatus.WAITING);
        if (!bookingDto.getItem().getAvailable()) {
            throw new ValidationException("This item not available");
        }
        if (bookingDto.getItem().getOwner() == userId) {
            throw new NoSuchElementException("User cannot book own item");
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart()) || bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("End date before start date");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Start date in the past");
        }
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new ValidationException("Start date cannot be equal to end date");
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto status(long userId, long bookingId, Boolean isApproved) {
        BookingStatus bookingStatus = isApproved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getStatus() == BookingStatus.APPROVED)
            throw new ValidationException("Status cannot be changed");

        if (booking.getItem().getOwner() != userId) {
            throw new NoSuchElementException("Booker cannot change rating status");
        }
        booking.setStatus(bookingStatus);
        bookingRepository.save(booking);
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto getBookingById(Long userId, Long bookingId) {
        userRepository.findById(userId).orElseThrow();
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (!(Objects.equals(booking.getBooker().getId(), userId) || Objects.equals(booking.getItem().getOwner(), userId)))
            throw new NoSuchElementException(String.format("User_id = %d и booking_id = %d not connected", userId, bookingId));
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsUser(long userId, String bookingState, int from, int size) {
        userRepository.findById(userId).orElseThrow();
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Booking> result = null;
        try {
            switch (BookingState.valueOf(bookingState)) {
                case ALL:
                    result = bookingRepository.findAllByBookerIdOrderByStartDesc(userId, pageRequest);
                    break;
                case PAST:
                    result = bookingRepository.findAllByBookerIdAndEndIsBeforeOrderByStartDesc(
                            userId,
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case CURRENT:
                    result = bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                            userId,
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case FUTURE:
                    result = bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(
                            userId,
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case WAITING:
                    result = bookingRepository.findAllByBookerIdAndStatus(
                            userId,
                            BookingStatus.WAITING,
                            pageRequest);
                    break;
                case REJECTED:
                    result = bookingRepository.findAllByBookerIdAndStatus(userId,
                            BookingStatus.REJECTED,
                            pageRequest);
                    break;
            }
        } catch (IllegalArgumentException ex) {
            throw new UnsupportedStateException("Unknown state: " + bookingState);
        }
        return result.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingsOwner(long ownerId, String bookingState, int from, int size) throws IllegalArgumentException {
        userRepository.findById(ownerId).orElseThrow();
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Booking> result = null;
        try {
            switch (BookingState.valueOf(bookingState)) {
                case ALL:
                    result = bookingRepository.findAllByItem_OwnerOrderByStartDesc(
                            ownerId,
                            pageRequest);
                    break;
                case PAST:
                    result = bookingRepository.findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(
                            ownerId,
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case CURRENT:
                    result = bookingRepository.findAllByItem_OwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(
                            ownerId,
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case FUTURE:
                    result = bookingRepository.findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(
                            ownerId,
                            LocalDateTime.now(),
                            pageRequest);
                    break;
                case WAITING:
                    result = bookingRepository.findAllByItem_OwnerAndStatus(
                            ownerId,
                            BookingStatus.WAITING,
                            pageRequest);
                    break;
                case REJECTED:
                    result = bookingRepository.findAllByItem_OwnerAndStatus(
                            ownerId,
                            BookingStatus.REJECTED,
                            pageRequest);
                    break;
            }
        } catch (IllegalArgumentException ex) {
            throw new UnsupportedStateException("Unknown state: " + bookingState);
        }
        return result.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    private BookingDto appropriateBookerAndItem(long userId, BookingDto bookingDto) {
        User user = userRepository.findById(userId).orElseThrow();
        try {
            Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow();
            bookingDto.setItem(item);
        } catch (NoSuchElementException e) {
            throw new ValidationFieldsException("item not found");
        }
        bookingDto.setBooker(user);
        return bookingDto;
    }

}
