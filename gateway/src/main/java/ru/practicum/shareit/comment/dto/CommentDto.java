package ru.practicum.shareit.comment.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    Long id;
    @NotBlank
    String text;
    Long item;
    String authorName;
    LocalDateTime created;
}
