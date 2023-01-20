package ru.practicum.shareit.item.dao.impl;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Optional;

@Component
public class ItemDaoImpl implements ItemDao {

    private int id = 1;

    public Optional<Item> add(Item item) {
//        validation(user);
//        user.setId(id);
//        userStorage.put(id,user);
//        id++;
        return Optional.of(item);
    }
}
