package ru.practicum.shareit.item.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.comment.dao.CommentRepository;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userServiceImpl;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    private User user1;

    private User user2;

    private Item item1;

    private Booking booking1;

    private Booking booking2;

    private Comment comment1;

    private LocalDateTime now;

    @BeforeEach
    void beforeEach() {
        now = LocalDateTime.now();
//        LocalDateTime start = now.plusDays(1);
//        LocalDateTime end = now.plusDays(2);

        user1 = new User(1L, "User1 name", "user1@mail.com", new HashSet<>());
        userRepository.save(user1);
        user2 = new User(2L, "User2 name", "user2@mail.com", new HashSet<>());
        userRepository.save(user2);

        item1 = new Item();
        item1.setId(1L);
        item1.setName("Item1 name");
        item1.setDescription("Item1 description");
        item1.setAvailable(true);
        item1.setOwner(1L);
        item1.setRequestId(1L);

        booking1 = new Booking(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                item1,
                user1,
                BookingStatus.WAITING);

        booking2 = new Booking(
                2L,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(3),
                item1,
                user1,
                BookingStatus.WAITING);
    }

    @Test
    void create() {
        when(userRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user1));

        when(repository.save(any(Item.class)))
                .thenReturn(item1);

        when(userServiceImpl.get(anyLong()))
                .thenReturn(UserMapper.toUserDto(user1));

        ItemDto itemDto = itemService.create(user1.getId(), ItemMapper.toItemDto(item1));

        assertEquals(1, itemDto.getId());
        assertEquals("Item1 name", itemDto.getName());
        assertEquals("Item1 description", itemDto.getDescription());
        assertEquals(true, itemDto.getAvailable());
    }

    @Test
    void update() {

        when(repository.findItemByIdAndOwner(anyLong(), anyLong()))
                .thenReturn(Optional.ofNullable(item1));
        when(repository.save(any(Item.class)))
                .thenReturn(item1);

        ItemDto itemDto = itemService.update(user1.getId(), item1.getId(), ItemMapper.toItemDto(item1));

        assertEquals(1, itemDto.getId());
        assertEquals("Item1 name", itemDto.getName());
        assertEquals("Item1 description", itemDto.getDescription());
        assertEquals(true, itemDto.getAvailable());
    }

    @Test
    void get() {
        when(repository.findItemById(anyLong()))
                .thenReturn(Optional.ofNullable(item1));
        when(bookingRepository.findAllByItemIdAndBooker_IdNotAndStatusNot(anyLong(), anyLong(), any()))
                .thenReturn(new ArrayList<>(List.of(booking1, booking2)));

        ItemDto itemDtoBooking = itemService.get(user1.getId(), item1.getId());

        assertEquals(1, itemDtoBooking.getId());
        assertEquals("Item1 name", itemDtoBooking.getName());
        assertEquals("Item1 description", itemDtoBooking.getDescription());
        assertEquals(true, itemDtoBooking.getAvailable());
    }

    @Test
    void getAll() {
        when(repository.findItemsByOwner(anyLong()))
                .thenReturn(List.of(item1));


        List<ItemDto> itemDtoBooking = itemService.getAll(user1.getId());

        assertEquals(1, itemDtoBooking.size());
        assertEquals(1, itemDtoBooking.get(0).getId());
        assertEquals("Item1 name", itemDtoBooking.get(0).getName());
        assertEquals("Item1 description", itemDtoBooking.get(0).getDescription());
    }

    @Test
    void search() {
        when(repository.searchItems(anyString()))
                .thenReturn(List.of(item1));

        List<ItemDto> itemDto = itemService.search("iTem1");

        assertEquals(1, itemDto.size());
        assertEquals(1, itemDto.get(0).getId());
        assertEquals("Item1 name", itemDto.get(0).getName());
        assertEquals("Item1 description", itemDto.get(0).getDescription());
        assertEquals(true, itemDto.get(0).getAvailable());
    }
}