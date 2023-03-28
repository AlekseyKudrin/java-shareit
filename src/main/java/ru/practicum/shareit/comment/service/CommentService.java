package ru.practicum.shareit.comment.service;

import ru.practicum.shareit.comment.model.CommentDto;

public interface CommentService {
    CommentDto addComment(long userId, long itemId, CommentDto commentDto);
}
