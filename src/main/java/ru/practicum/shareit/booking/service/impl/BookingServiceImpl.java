package ru.practicum.shareit.booking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.cons.BookingStatus;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        if (!bookingDto.getItem().getAvailable())
            throw new ValidationException("Этот item недоступен");
        if (bookingDto.getItem().getOwner() == userId) {
            throw new NoSuchElementException("User не может забронировать свой же item");
        }
        if (bookingDto.getEnd().isBefore(bookingDto.getStart()) || bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата окончания раньше даты начала");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата начала в прошлом");
        }
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto status(long userId, long bookingId, Boolean isApproved) {
        BookingStatus bookingStatus = isApproved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getStatus() == BookingStatus.APPROVED)
            throw new ValidationException("Статус не может быть изменен");
        if (booking.getItem().getOwner() != userId) {
            throw new NoSuchElementException("Букер не может изменить статус бронирования");
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
            throw new NoSuchElementException(String.format("User_id = %d и booking_id = %d не связаны", userId, bookingId));
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsUser(long userId, String bookingState) {
        userRepository.findById(userId).orElseThrow();
        List<Booking> result;
        switch (bookingState) {
            case "ALL":
                result = bookingRepository.findAllByBookerIdOrderByStartDesc(userId);
                break;
            case "PAST":
                result = bookingRepository.findAllByBookerIdAndEndIsBeforeOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case "CURRENT":
                result = bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(userId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case "FUTURE":
                result = bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(userId, LocalDateTime.now());
                break;
            case "WAITING":
                result = bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.WAITING);
                break;
            case "REJECTED":
                result = bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.REJECTED);
                break;
            default:
                throw new ValidationException("Unknown state: " + bookingState);
        }
        return result.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingsOwner(long ownerId, String bookingState) {
        userRepository.findById(ownerId).orElseThrow();
        List<Booking> result = new ArrayList<>();
        switch (bookingState) {
            case "ALL":
                result = bookingRepository.findAllByItem_OwnerOrderByStartDesc(ownerId);
                break;
            case "PAST":
                result = bookingRepository.findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(ownerId, LocalDateTime.now());
                break;
            case "CURRENT":
                result = bookingRepository.findAllByItem_OwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(ownerId, LocalDateTime.now(), LocalDateTime.now());
                break;
            case "FUTURE":
                result = bookingRepository.findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(ownerId, LocalDateTime.now());
                break;
            case "WAITING":
                result = bookingRepository.findAllByItem_OwnerAndStatus(ownerId, BookingStatus.WAITING);
                break;
            case "REJECTED":
                result = bookingRepository.findAllByItem_OwnerAndStatus(ownerId, BookingStatus.REJECTED);
                break;
            default:
                throw new ValidationException("Unknown state: " + bookingState);
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
