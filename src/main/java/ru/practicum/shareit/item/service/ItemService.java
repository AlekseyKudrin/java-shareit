package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.ItemDto;

import java.util.Map;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDto> create(Map<String, String> headers, ItemDto itemDto);
}
