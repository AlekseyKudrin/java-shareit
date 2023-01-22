package ru.practicum.shareit.item.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dao.UserDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ItemDaoImpl implements ItemDao {
    private final UserDao userDao;
    private int id = 1;

    private Map<Integer, Item> itemStorage = new HashMap<>();

    public Optional<Item> add(Item item) {
        validation(item);
        item.setId(id);
        itemStorage.put(id, item);
        id++;
        return Optional.of(item);
    }

    @Override
    public Optional<Item> update(int itemId, Item item) {
        Item i2 = itemStorage.get(itemId);
        if (!i2.getOwner().equals(item.getOwner())){
            throw new ValidationFieldsException("the owner of the item is not correct");
        }
        if (item.getName() != null) {
            i2.setName(item.getName());
        }
        if (item.getDescription() != null) {
            i2.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            i2.setAvailable(item.getAvailable());
        }
        return get(i2.getId());
    }

    @Override
    public Optional<Item> get(int itemId) {
        return Optional.of(itemStorage.get(itemId));
    }

    @Override
    public Collection<Item> getAll(Integer userId) {
        Collection<Item> items = new ArrayList<>();
        for (Item item : itemStorage.values()) {
            if (item.getOwner() == userId) {
                items.add(item);
            }
        }
        return items;
    }

    @Override
    public Collection<Item> search(String text) {
        Collection<Item> items = new ArrayList<>();
        if (text.length() == 0) return items;
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
