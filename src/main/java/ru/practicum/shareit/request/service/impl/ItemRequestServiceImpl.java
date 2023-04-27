package ru.practicum.shareit.request.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.request.dao.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequestMapper;
import ru.practicum.shareit.request.service.ItemRequestService;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRepository itemRepository;

    private final ItemRequestRepository requestRepository;

    private final UserRepository userRepository;

    @Override
    public ItemRequestDto create(long userId, ItemRequestDto itemRequestDto) {
        User user = userRepository.findById(userId).orElseThrow();
        itemRequestDto.setCreated(LocalDateTime.now());
       return ItemRequestMapper.toItemRequestDto(requestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto, user)));
    }

    @Override
    public List<ItemRequestDto> getRequestsOwner(long userId) {
        userRepository.findById(userId).orElseThrow();
        List<ItemRequestDto> responseList = requestRepository.findAllByRequestorId(userId).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
        return setItemsToRequests(responseList);
    }

    @Override
    public List<ItemRequestDto> getAll(long userId, int from, int size) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ItemRequestDto> responseList = requestRepository.findAllPageable(userId, pageRequest).stream()
                .map(ItemRequestMapper::toItemRequestDto)
                .collect(Collectors.toList());
        return setItemsToRequests(responseList);
    }

    @Override
    public ItemRequestDto getById(long userId, long requestId) {
        userRepository.findById(userId).orElseThrow();
        ItemRequest itemRequest = requestRepository.findById(requestId).get();
        List<ItemDto> items = itemRepository.findItemByRequestId(requestId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        ItemRequestDto itemRequestDtoResponse = ItemRequestMapper.toItemRequestDto(itemRequest);
        itemRequestDtoResponse.setItems(items);
        return itemRequestDtoResponse;
    }

    private List<ItemRequestDto> setItemsToRequests(List<ItemRequestDto> itemRequestDtoResponseList) {
        Map<Long, ItemRequestDto> requests = itemRequestDtoResponseList.stream()
                .collect(Collectors.toMap(ItemRequestDto::getId, film -> film, (a, b) -> b));
        List<Long> ids = requests.values().stream()
                .map(ItemRequestDto::getId)
                .collect(Collectors.toList());
        List<ItemDto> items = itemRepository.searchByRequestsId(ids).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
        items.forEach(itemDto -> requests.get(itemDto.getRequestId()).getItems().add(itemDto));
        return new ArrayList<>(requests.values());
    }
}
