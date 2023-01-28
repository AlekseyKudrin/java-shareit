package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemDao itemDao;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        userService.validationOwner(ItemMapper.toItem(userId, itemDto).getOwner());
        Item item = itemDao.add(ItemMapper.toItem(userId, itemDto));
        log.info("Item successfully created");
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Item item = itemDao.get(itemId);
        Item itemUpdate = ItemMapper.toItem(userId, itemDto);
        if (!itemUpdate.getOwner().equals(item.getOwner())) {
            throw new ValidationFieldsException("the owner of the item is not correct");
        }
        itemUpdate = itemDao.update(itemId, itemUpdate);
        log.info("Item successfully updated");
        return ItemMapper.toItemDto(itemUpdate);
    }

    @Override
    public ItemDto get(long itemId) {
        Item item = itemDao.get(itemId);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        List<Item> items = itemDao.getAll(userId);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item :
                items) {
            itemDtos.add(ItemMapper.toItemDto(item));
        }
        return itemDtos;
    }

    @Override
    public List<ItemDto> search(String text) {
        List<Item> items = itemDao.search(text);
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item :
                items) {
            itemDtos.add(ItemMapper.toItemDto(item));
        }
        return itemDtos;
    }
}
