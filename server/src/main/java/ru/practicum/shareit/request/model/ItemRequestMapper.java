package ru.practicum.shareit.request.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {

    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, User user) {
        return new ItemRequest(
                itemRequestDto.getId(),
                user,
                itemRequestDto.getDescription(),
                itemRequestDto.getCreated());
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getRequestor().getId(),
                itemRequest.getDescription(),
                itemRequest.getCreated(),
                new ArrayList<>());
    }

}
