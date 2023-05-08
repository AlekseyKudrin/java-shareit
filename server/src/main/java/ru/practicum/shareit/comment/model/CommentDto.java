package ru.practicum.shareit.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    Long id;

    String text;
    Long item;
    String authorName;
    LocalDateTime created;
}
