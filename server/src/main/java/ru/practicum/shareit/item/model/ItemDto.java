package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.BookingTime;
import ru.practicum.shareit.comment.model.CommentDto;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemDto {
    Long id;

    String name;

    String description;

    Boolean available;
    Long ownerId;
    Long requestId;
    BookingTime lastBooking;
    BookingTime nextBooking;
    List<CommentDto> comments;
}
