package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static Item toItem(long userId, ItemDto itemDto) {
        return new Item(
                itemDto.getId() != 0 ? itemDto.getId() : 0,
                itemDto.getName() != null ? itemDto.getName() : null,
                itemDto.getDescription() != null ? itemDto.getDescription() : null,
                itemDto.getAvailable() != null ? itemDto.getAvailable() : null,
                userId,
                null
        );
    }

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner(),
                null
        );
    }
}
