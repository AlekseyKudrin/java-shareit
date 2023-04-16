package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.model.ItemRequestDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto create(long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> getRequestsOwner(long userId);

    List<ItemRequestDto> getAll(long userId, long from, long size);

    ItemRequestDto getById(long userId, long requestId);
}
