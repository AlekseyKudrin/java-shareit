package ru.practicum.shareit.item.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
public class ItemDaoImpl implements ItemDao {
    private final UserDao userDao;
    private final Map<Long, Item> itemStorage = new HashMap<>();
    private long id = 1;

    public Item add(Item item) {
        validation(item);
        item.setId(id);
        itemStorage.put(id, item);
        id++;
        return item;
    }

    @Override
    public Item update(long itemId, Item item) {
        validation(item);
        Item i2 = itemStorage.get(itemId);
        if (!i2.getOwner().equals(item.getOwner())) {
            throw new ValidationFieldsException("the owner of the item is not correct");
        }
        if (item.getName() != null && !item.getName().isBlank()) {
            i2.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            i2.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            i2.setAvailable(item.getAvailable());
        }
        return get(i2.getId());
    }

    @Override
    public Item get(long itemId) {
        return itemStorage.get(itemId);
    }

    @Override
    public List<Item> getAll(long userId) {
        List<Item> items = new ArrayList<>();
        for (Item item : itemStorage.values()) {
            if (Objects.equals(item.getOwner(), userId)) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public List<Item> search(String text) {
        List<Item> items = new ArrayList<>();
        if (text.isBlank()) return List.of();
        for (Item item : itemStorage.values()) {
            if (item.getAvailable()) {
                StringBuilder description = new StringBuilder(
                        item.getDescription().toLowerCase() +
                                item.getName().toLowerCase());
                if (description.toString().contains(text.toLowerCase())) {
                    items.add(item);
                }
            }
        }
        return items;
    }

    private void validation(Item item) {
        userDao.get(item.getOwner());
    }
}
