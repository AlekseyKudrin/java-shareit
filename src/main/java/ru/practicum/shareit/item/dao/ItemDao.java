package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemDao {
    Item add(Item item);

    Item update(long itemId, Item toItem);

    Item get(long itemId);

    List<Item> getAll(long userId);

    List<Item> search(String text);
}
