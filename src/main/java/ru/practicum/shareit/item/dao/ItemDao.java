package ru.practicum.shareit.item.dao;

import ru.practicum.shareit.item.model.Item;

import java.util.Optional;

public interface ItemDao {
    Optional<Item> add(Item item);
}
