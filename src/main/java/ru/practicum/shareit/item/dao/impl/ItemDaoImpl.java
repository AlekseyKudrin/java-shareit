package ru.practicum.shareit.item.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;

import java.util.*;


@Repository
@RequiredArgsConstructor
public class ItemDaoImpl implements ItemDao {
    private final Map<Long, Item> itemStorage = new HashMap<>();
    private long id = 1;

    public Item add(Item item) {
        item.setId(id);
        itemStorage.put(id, item);
        id++;
        return item;
    }

    @Override
    public Item update(long itemId, Item item) {
        Item itemUpdate = itemStorage.get(itemId);
//        if (!itemUpdate.getOwner().equals(item.getOwner())) {
//            throw new ValidationFieldsException("the owner of the item is not correct");
//        }
        if (item.getName() != null && !item.getName().isBlank()) {
            itemUpdate.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            itemUpdate.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemUpdate.setAvailable(item.getAvailable());
        }
        return get(itemUpdate.getId());
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
}
