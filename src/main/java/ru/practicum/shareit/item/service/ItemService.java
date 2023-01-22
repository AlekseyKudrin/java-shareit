package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.model.ItemDto;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ItemService {
    Optional<ItemDto> create(Map<String, String> headers, ItemDto itemDto);

    Optional<ItemDto> update(Map<String, String> headers, int itemId, ItemDto itemDto);

    Optional<ItemDto> get(int itemId);

    Collection<ItemDto> getAll(Map<String, String> headers);

    Collection<ItemDto> search(String text);
}
