package ru.practicum.shareit.booking.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId, PageRequest pageRequest);

    List<Booking> findAllByItem_OwnerOrderByStartDesc(Long itemOwnerId, PageRequest pageRequest);

    List<Booking> findAllByBookerIdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime end, PageRequest pageRequest);

    List<Booking> findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Long bookerId, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    List<Booking> findAllByBookerIdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime start, PageRequest pageRequest);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status, PageRequest pageRequest);

    List<Booking> findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(Long itemOwnerId, LocalDateTime end, PageRequest pageRequest);

    List<Booking> findAllByItem_OwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(Long itemOwnerId, LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    List<Booking> findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(Long itemOwnerId, LocalDateTime start, PageRequest pageRequest);

    List<Booking> findAllByItem_OwnerAndStatus(Long itemOwnerId, BookingStatus status, PageRequest pageRequest);

    List<Booking> findAllByItemIdAndBooker_IdNotAndStatusNot(Long itemId, Long bookerId, BookingStatus status);

    List<Booking> findAllByItemIdAndBooker_IdAndStatusAndEndIsBefore(Long itemId, Long bookerId, BookingStatus status, LocalDateTime end);
}
