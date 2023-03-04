package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        return null;
    }

    @Override
    public ItemDto get(long itemId) {
        return null;
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        return null;
    }

    @Override
    public List<ItemDto> search(String text) {
        return null;
    }
}
