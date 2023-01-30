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
    private final Map<Long, List<Item>> userItemIndex = new LinkedHashMap<>();
    private long id = 1;

    public Item add(Item item) {
        item.setId(id);
        itemStorage.put(id, item);
        id++;
        final List<Item> items = userItemIndex.computeIfAbsent(item.getOwner(), k -> new ArrayList<>());
        items.add(item);
        userItemIndex.put(id, items);
        return item;
    }

    @Override
    public Item update(long itemId, Item item) {
        Item itemUpdate = itemStorage.get(itemId);
        if (item.getName() != null && !item.getName().isBlank()) {
            itemUpdate.setName(item.getName());
        }
        if (item.getDescription() != null && !item.getDescription().isBlank()) {
            itemUpdate.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemUpdate.setAvailable(item.getAvailable());
        }
        return itemUpdate;
    }

    @Override
    public Item get(long itemId) {
        return itemStorage.get(itemId);
    }

    @Override
    public List<Item> getAll(long userId) {
        return userItemIndex.get(userId);
    }

    @Override
    public List<Item> search(String text) {
        final String searchText = text.toLowerCase(Locale.ENGLISH);
        return itemStorage.values()
                .stream()
                .filter(i -> i.getAvailable()
                        && (i.getName().toLowerCase().contains(searchText)
                        || i.getDescription().toLowerCase().contains(searchText))).toList();
    }
}
