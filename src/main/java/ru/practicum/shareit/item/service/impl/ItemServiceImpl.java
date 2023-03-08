package ru.practicum.shareit.item.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dao.BookingRepository;
import ru.practicum.shareit.comment.dao.CommentRepository;
import ru.practicum.shareit.comment.model.Comment;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.item.dao.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemDto;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final UserService userService;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        return ItemMapper.toItemDto(itemRepository.save(ItemMapper.toItem(userService.get(userId).getId(),itemDto)));
    }

    @Override
    public ItemDto update(long userId, long itemId, ItemDto itemDto) {
        Optional<Item> item = itemRepository.findItemByIdAndOwner(itemId, userId);
        Item itemUpdate = ItemMapper.toItem(userId, itemDto);
        if (item.isPresent() && itemUpdate.getOwner().equals(item.orElseThrow().getOwner())) {
            return ItemMapper.toItemDto(
                    itemRepository.save(updateItem(item.orElseThrow(), itemUpdate)));
        }else{
            throw new ValidationFieldsException("the owner of the item is not correct");
        }
    }

    @Override
    public ItemDto get(long itemId) {
        Optional<Item> item = itemRepository.findItemById(itemId);
        if (item.isPresent()) {
            return ItemMapper.toItemDto(item.orElseThrow());
        }else{
            throw new ValidationFieldsException("item not found");
        }
    }

    @Override
    public List<ItemDto> getAll(long userId) {
        return itemRepository.findItemsByOwner(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isBlank() || text.isEmpty()) {
            return new ArrayList<>();
        }
        return itemRepository.searchItems(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private Item updateItem(Item item, Item itemUpdate) {
        if (itemUpdate.getName() != null) item.setName(itemUpdate.getName());
        if (itemUpdate.getDescription() != null) item.setDescription(itemUpdate.getDescription());
        if (itemUpdate.getAvailable() != null) item.setAvailable(itemUpdate.getAvailable());
        if (itemUpdate.getOwner() != null) item.setOwner(itemUpdate.getOwner());
        if (itemUpdate.getRequestId() != null) item.setRequestId(itemUpdate.getRequestId());
        return item;
    }

    private void addComments(Item item) {
        List<Comment> comments = commentRepository.findAllByItemId(item.getId());
        item.setComments(comments);
    }

/*    private void addBookings(ItemDto itemDto, long bookerId) {
        Comparator<Booking> comparator = (b1, b2) -> b1.getStart().isBefore(b2.getStart()) ? -1 : b1.getStart().isAfter(b2.getStart()) ? 1 : 0;
        List<Booking> bookings = bookingRepository.findAllByItemIdAndBooker_IdNotAndStatusNot(item.getId(), bookerId, BookingStatus.REJECTED);
        bookings.sort(comparator);
        for (Booking booking : bookings) {
            itemDto.setNextBooking(BookingMapper.toDtoWithId(booking));
        }
    }*/
}
