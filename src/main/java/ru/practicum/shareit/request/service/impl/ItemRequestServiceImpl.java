package ru.practicum.shareit.request.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    @Override
    public ItemRequestDto create(long userId, ItemRequestDto itemRequestDto) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getRequestsOwner(long userId) {
        return null;
    }

    @Override
    public List<ItemRequestDto> getAll(long userId, long from, long size) {
        return null;
    }

    @Override
    public ItemRequestDto getById(long userId, long requestId) {
        return null;
    }
}
