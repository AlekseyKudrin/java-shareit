package ru.practicum.shareit.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

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
}
