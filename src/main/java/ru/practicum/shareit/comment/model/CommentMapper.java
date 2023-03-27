package ru.practicum.shareit.comment.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText()
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getItem().getId(),
                comment.getUser().getName(),
                comment.getCreated()
        );

    }
}
