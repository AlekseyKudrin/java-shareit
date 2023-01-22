package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemDao;
import ru.practicum.shareit.exceptionHandler.exception.ValidationHeadersException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;

    @Override
    public Optional<ItemDto> create(Map<String, String> headers, ItemDto itemDto) {
        Item item = itemDao.add(ItemMapper.toItem(getUserId(headers), itemDto)).orElse(new Item());
        log.info("Item successfully created");
        return Optional.of(ItemMapper.toItemDto(item));
    }

    @Override
    public Optional<ItemDto> update(Map<String, String> headers, int itemId, ItemDto itemDto) {
            Item item = itemDao.update(itemId, ItemMapper.toItem(getUserId(headers), itemDto)).orElse(new Item());
        log.info("Item successfully updated");
        return Optional.of(ItemMapper.toItemDto(item));
    }

    @Override
    public Optional<ItemDto> get(int itemId) {
        Item item = itemDao.get(itemId).orElse(new Item());
        return Optional.of(ItemMapper.toItemDto(item));
    }

    @Override
    public Collection<ItemDto> getAll(Map<String, String> headers) {
        Collection<Item> items = itemDao.getAll(getUserId(headers));
        Collection<ItemDto> itemDtos = new ArrayList<>();
        for (Item item :
                items) {
            itemDtos.add(ItemMapper.toItemDto(item));
        }
        return itemDtos;
    }

    @Override
    public Collection<ItemDto> search(String text) {
        Collection<Item> items = itemDao.search(text);
        Collection<ItemDto> itemDtos = new ArrayList<>();
        for (Item item :
                items) {
            itemDtos.add(ItemMapper.toItemDto(item));
        }
        return itemDtos;
    }

    private Integer getUserId(Map<String, String> headers) {
        if (headers.containsKey("x-sharer-user-id")) {
            return Integer.parseInt(headers.get("x-sharer-user-id"));
        } else {
            throw new ValidationHeadersException("Header x-sharer-user-id not found");
        }
    }
}
