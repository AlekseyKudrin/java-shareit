package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

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
}
