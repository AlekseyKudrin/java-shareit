package ru.practicum.shareit.booking.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingDto;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.exceptionHandler.exception.UnsupportedStateException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    private LocalDateTime start;

    private LocalDateTime end;

    private User user1;

    private User user2;

    private Item item1;

    private Booking booking1;

    @BeforeEach
    void beforeEach() {

        start = LocalDateTime.now().plusDays(1);
        end = LocalDateTime.now().plusDays(2);

        user1 = new User(1L, "User1 name", "user1@mail.com", new HashSet<>());
        user2 = new User(2L, "User2 name", "user2@mail.com", new HashSet<>());

        item1 = new Item();
        item1.setId(1L);
        item1.setName("Item1 name");
        item1.setDescription("Item1 description");
        item1.setAvailable(true);
        item1.setOwner(1L);
        item1.setRequestId(1L);

        booking1 = new Booking();
        booking1.setId(1L);
        booking1.setStart(start);
        booking1.setEnd(end);
        booking1.setItem(item1);
        booking1.setBooker(user1);
        booking1.setStatus(BookingStatus.WAITING);
    }

    @Test
    void create() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        when(itemRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(item1));

        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking1);

        BookingDto bookingDtoResponse = bookingService.create(
                user2.getId(),
                BookingMapper.toBookingDto(booking1)
        );

        assertEquals(1, bookingDtoResponse.getId());
        assertEquals(start, bookingDtoResponse.getStart());
        assertEquals(end, bookingDtoResponse.getEnd());
        assertEquals(item1, bookingDtoResponse.getItem());
        assertEquals(user1, bookingDtoResponse.getBooker());


        booking1.getItem().setAvailable(false);
        ValidationException responses = assertThrows(ValidationException.class,
                () -> bookingService.create(user2.getId(),
                        BookingMapper.toBookingDto(booking1)));

        assertEquals("This item not available", responses.getMessage());


        booking1.getItem().setAvailable(true);
        booking1.getItem().setOwner(2L);
        NoSuchElementException responses2 = assertThrows(NoSuchElementException.class,
                () -> bookingService.create(user2.getId(),
                        BookingMapper.toBookingDto(booking1)));

        assertEquals("User cannot book own item", responses2.getMessage());


        booking1.getItem().setOwner(1L);
        booking1.setEnd(start.minusDays(1));
        ValidationException responses3 = assertThrows(ValidationException.class,
                () -> bookingService.create(user2.getId(),
                        BookingMapper.toBookingDto(booking1)));

        assertEquals("End date before start date", responses3.getMessage());
    }

    @Test
    void status() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(booking1));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.save(any(Booking.class)))
                .thenReturn(booking1);

        BookingDto bookingDtoResponse = bookingService.status(
                user1.getId(),
                booking1.getId(),
                true);

        assertEquals(1, bookingDtoResponse.getId());
        assertEquals(start, bookingDtoResponse.getStart());
        assertEquals(end, bookingDtoResponse.getEnd());
        assertEquals(item1, bookingDtoResponse.getItem());
        assertEquals(user1, bookingDtoResponse.getBooker());
        assertEquals(BookingStatus.APPROVED, bookingDtoResponse.getStatus());
    }

    @Test
    void getBookingById() {
        when(bookingRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(booking1));
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        BookingDto bookingDtoResponse = bookingService.getBookingById(
                booking1.getId(),
                user1.getId());

        assertEquals(1, bookingDtoResponse.getId());
        assertEquals(start, bookingDtoResponse.getStart());
        assertEquals(end, bookingDtoResponse.getEnd());
        assertEquals(item1, bookingDtoResponse.getItem());
        assertEquals(user1, bookingDtoResponse.getBooker());
        assertEquals(BookingStatus.WAITING, bookingDtoResponse.getStatus());

        NoSuchElementException responses = assertThrows(NoSuchElementException.class,
                () -> bookingService.getBookingById(user2.getId(),
                        5L));

        assertEquals("User_id = 2 и booking_id = 5 not connected", responses.getMessage());
    }

    @Test
    void getAllBookingsUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByBookerIdAndEndIsBeforeOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByBookerIdAndStartIsBeforeAndEndIsAfterOrderByStartDesc(anyLong(), any(), any(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByBookerIdAndStartIsAfterOrderByStartDesc(anyLong(), any(), any()))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByBookerIdAndStatus(anyLong(), any(), any()))
                .thenReturn(List.of(booking1));


        List<BookingDto> responses1 = bookingService.getAllBookingsUser(user1.getId(),
                "ALL",
                0,
                10);

        assertEquals(1, responses1.size());
        assertEquals(1, responses1.get(0).getId());
        assertEquals(start, responses1.get(0).getStart());
        assertEquals(end, responses1.get(0).getEnd());
        assertEquals(item1, responses1.get(0).getItem());
        assertEquals(user1, responses1.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses1.get(0).getStatus());

        List<BookingDto> responses2 = bookingService.getAllBookingsUser(user1.getId(),
                "PAST",
                0,
                10);

        assertEquals(1, responses2.size());
        assertEquals(1, responses2.get(0).getId());
        assertEquals(start, responses2.get(0).getStart());
        assertEquals(end, responses2.get(0).getEnd());
        assertEquals(item1, responses2.get(0).getItem());
        assertEquals(user1, responses2.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses2.get(0).getStatus());

        List<BookingDto> bookingDtoResponses = bookingService.getAllBookingsUser(user1.getId(),
                "CURRENT",
                0,
                10);

        assertEquals(1, bookingDtoResponses.size());
        assertEquals(1, bookingDtoResponses.get(0).getId());
        assertEquals(start, bookingDtoResponses.get(0).getStart());
        assertEquals(end, bookingDtoResponses.get(0).getEnd());
        assertEquals(item1, bookingDtoResponses.get(0).getItem());
        assertEquals(user1, bookingDtoResponses.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, bookingDtoResponses.get(0).getStatus());

        List<BookingDto> responses3 = bookingService.getAllBookingsUser(user1.getId(),
                "FUTURE",
                0,
                10);

        assertEquals(1, responses3.size());
        assertEquals(1, responses3.get(0).getId());
        assertEquals(start, responses3.get(0).getStart());
        assertEquals(end, responses3.get(0).getEnd());
        assertEquals(item1, responses3.get(0).getItem());
        assertEquals(user1, responses3.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses3.get(0).getStatus());

        List<BookingDto> responses4 = bookingService.getAllBookingsUser(user1.getId(),
                "WAITING",
                0,
                10);

        assertEquals(1, responses4.size());
        assertEquals(1, responses4.get(0).getId());
        assertEquals(start, responses4.get(0).getStart());
        assertEquals(end, responses4.get(0).getEnd());
        assertEquals(item1, responses4.get(0).getItem());
        assertEquals(user1, responses4.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses4.get(0).getStatus());

        List<BookingDto> responses5 = bookingService.getAllBookingsUser(user1.getId(),
                "REJECTED",
                0,
                10);

        assertEquals(1, responses5.size());
        assertEquals(1, responses5.get(0).getId());
        assertEquals(start, responses5.get(0).getStart());
        assertEquals(end, responses5.get(0).getEnd());
        assertEquals(item1, responses5.get(0).getItem());
        assertEquals(user1, responses5.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses5.get(0).getStatus());


        UnsupportedStateException responses6 = assertThrows(UnsupportedStateException.class,
                () -> bookingService.getAllBookingsUser(user1.getId(),
                        "Unknown",
                        0,
                        10));

        assertEquals("Unknown state: Unknown", responses6.getMessage());
    }

    @Test
    void getAllBookingsOwner() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.findAllByItem_OwnerOrderByStartDesc(anyLong(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(anyLong(), any(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByItem_OwnerAndStartIsBeforeAndEndIsAfterOrderByStartDesc(anyLong(), any(), any(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(anyLong(), any(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));
        when(bookingRepository.findAllByItem_OwnerAndStatus(anyLong(), any(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));

        List<BookingDto> responses1 = bookingService.getAllBookingsOwner(user1.getId(),
                "ALL",
                0,
                10);

        assertEquals(1, responses1.size());
        assertEquals(1, responses1.get(0).getId());
        assertEquals(start, responses1.get(0).getStart());
        assertEquals(end, responses1.get(0).getEnd());
        assertEquals(item1, responses1.get(0).getItem());
        assertEquals(user1, responses1.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses1.get(0).getStatus());

        List<BookingDto> responses2 = bookingService.getAllBookingsOwner(user1.getId(),
                "PAST",
                0,
                10);

        assertEquals(1, responses2.size());
        assertEquals(1, responses2.get(0).getId());
        assertEquals(start, responses2.get(0).getStart());
        assertEquals(end, responses2.get(0).getEnd());
        assertEquals(item1, responses2.get(0).getItem());
        assertEquals(user1, responses2.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses2.get(0).getStatus());

        List<BookingDto> responses3 = bookingService.getAllBookingsOwner(user1.getId(),
                "CURRENT",
                0,
                10);


        assertEquals(1, responses3.size());
        assertEquals(1, responses3.get(0).getId());
        assertEquals(start, responses3.get(0).getStart());
        assertEquals(end, responses3.get(0).getEnd());
        assertEquals(item1, responses3.get(0).getItem());
        assertEquals(user1, responses3.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses3.get(0).getStatus());

        List<BookingDto> responses4 = bookingService.getAllBookingsOwner(user1.getId(),
                "FUTURE",
                0,
                10);

        assertEquals(1, responses4.size());
        assertEquals(1, responses4.get(0).getId());
        assertEquals(start, responses4.get(0).getStart());
        assertEquals(end, responses4.get(0).getEnd());
        assertEquals(item1, responses4.get(0).getItem());
        assertEquals(user1, responses4.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses4.get(0).getStatus());

        List<BookingDto> responses5 = bookingService.getAllBookingsOwner(user1.getId(),
                "WAITING",
                0,
                10);

        assertEquals(1, responses5.size());
        assertEquals(1, responses5.get(0).getId());
        assertEquals(start, responses5.get(0).getStart());
        assertEquals(end, responses5.get(0).getEnd());
        assertEquals(item1, responses5.get(0).getItem());
        assertEquals(user1, responses5.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses5.get(0).getStatus());

        List<BookingDto> responses6 = bookingService.getAllBookingsOwner(user1.getId(),
                "REJECTED",
                0,
                10);

        assertEquals(1, responses6.size());
        assertEquals(1, responses6.get(0).getId());
        assertEquals(start, responses6.get(0).getStart());
        assertEquals(end, responses6.get(0).getEnd());
        assertEquals(item1, responses6.get(0).getItem());
        assertEquals(user1, responses6.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, responses6.get(0).getStatus());

        UnsupportedStateException responses7 = assertThrows(UnsupportedStateException.class,
                () -> bookingService.getAllBookingsOwner(user1.getId(),
                        "Unknown",
                        0,
                        10));

        assertEquals("Unknown state: Unknown", responses7.getMessage());

    }
}