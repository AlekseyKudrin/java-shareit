package ru.practicum.shareit.comment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.comment.dao.CommentRepository;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.comment.model.CommentDto;
import ru.practicum.shareit.comment.model.CommentMapper;
import ru.practicum.shareit.comment.service.CommentService;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;


    @Override
    public CommentDto addComment(long userId, long itemId, CommentDto commentDto) {
        UserDto userDto = userService.get(userId);
        Item item = itemRepository.findById(itemId).orElseThrow();
        List<Booking> bookings = bookingRepository
                .findAllByItemIdAndBooker_IdAndStatusAndEndIsBefore(itemId, userId, BookingStatus.APPROVED, LocalDateTime.now());

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setCreated(LocalDateTime.now());
        comment.setUser(UserMapper.toUser(userDto));
        comment.setItem(item);
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }
}
