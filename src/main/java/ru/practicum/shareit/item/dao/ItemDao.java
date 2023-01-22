package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;
import java.util.Optional;

public interface ItemDao {
    Optional<Item> add(Item item);

    Optional<Item> update(int itemId, Item toItem);

    Optional<Item> get(int itemId);

    Collection<Item> getAll(Integer userId);

    Collection<Item> search(String text);
}
