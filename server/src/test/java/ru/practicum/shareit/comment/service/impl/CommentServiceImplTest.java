package ru.practicum.shareit.comment.service.impl;

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
import ru.practicum.shareit.comment.model.CommentDto;
import ru.practicum.shareit.comment.model.CommentMapper;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

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
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private ItemRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private BookingRepository bookingRepository;

    private User user1;

    private Item item1;

    private Booking booking1;

    private Comment comment;


    @BeforeEach
    void beforeEach() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now.plusDays(1);
        LocalDateTime end = now.plusDays(2);

        booking1 = new Booking();
        booking1.setId(1L);
        booking1.setStart(start);
        booking1.setEnd(end);
        booking1.setItem(item1);
        booking1.setBooker(user1);
        booking1.setStatus(BookingStatus.WAITING);

        item1 = new Item();
        item1.setId(1L);
        item1.setName("Item1 name");
        item1.setDescription("Item1 description");
        item1.setAvailable(true);
        item1.setOwner(1L);
        item1.setRequestId(1L);

        user1 = new User(1L, "User1 name", "user1@mail.com", new HashSet<>());

        comment = new Comment();
        comment.setId(1L);
        comment.setText("Comment1 text");
        comment.setItem(item1);
        comment.setUser(user1);
        comment.setCreated(LocalDateTime.now());

    }

    @Test
    void addComment() {
        when(userService.get(anyLong()))
                .thenReturn(UserMapper.toUserDto(user1));
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(item1));
        when(bookingRepository.findAllByItemIdAndBooker_IdAndStatusAndEndIsBefore(
                anyLong(),
                anyLong(),
                any(BookingStatus.class),
                any()))
                .thenReturn(List.of(booking1));
        when(commentRepository.save(any(Comment.class)))
                .thenReturn(comment);


        CommentDto commentDto = commentService
                .addComment(1L, 1L, CommentMapper.toCommentDto(comment));

        assertEquals(1, commentDto.getId());
        assertEquals("Comment1 text", commentDto.getText());
        assertEquals("User1 name", commentDto.getAuthorName());
    }
}