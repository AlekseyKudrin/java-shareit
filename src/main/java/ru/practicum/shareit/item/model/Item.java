package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class Item {
    long id;
    String name;
    String description;
    Boolean available;
    Long owner;
}
