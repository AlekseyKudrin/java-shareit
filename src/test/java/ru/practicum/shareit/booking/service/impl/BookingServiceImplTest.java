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
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    }

    @Test
    void getAllBookingsUser() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.findAllByBookerIdOrderByStartDesc(anyLong(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));

        List<BookingDto> bookingDtoResponses = bookingService.getAllBookingsUser(user1.getId(),
                "ALL",
                0,
                10);

        assertEquals(1, bookingDtoResponses.size());
        assertEquals(1, bookingDtoResponses.get(0).getId());
        assertEquals(start, bookingDtoResponses.get(0).getStart());
        assertEquals(end, bookingDtoResponses.get(0).getEnd());
        assertEquals(item1, bookingDtoResponses.get(0).getItem());
        assertEquals(user1, bookingDtoResponses.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, bookingDtoResponses.get(0).getStatus());

    }

    @Test
    void getAllBookingsOwner() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));
        when(bookingRepository.findAllByItem_OwnerOrderByStartDesc(anyLong(), any(PageRequest.class)))
                .thenReturn(List.of(booking1));

        List<BookingDto> bookingDtoResponses = bookingService.getAllBookingsOwner(user1.getId(),
                "ALL",
                0,
                10);

        assertEquals(1, bookingDtoResponses.size());
        assertEquals(1, bookingDtoResponses.get(0).getId());
        assertEquals(start, bookingDtoResponses.get(0).getStart());
        assertEquals(end, bookingDtoResponses.get(0).getEnd());
        assertEquals(item1, bookingDtoResponses.get(0).getItem());
        assertEquals(user1, bookingDtoResponses.get(0).getBooker());
        assertEquals(BookingStatus.WAITING, bookingDtoResponses.get(0).getStatus());
    }
}