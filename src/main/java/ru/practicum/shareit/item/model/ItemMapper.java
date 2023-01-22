package ru.practicum.shareit.item.model;

public class ItemMapper {
    public static Item toItem(Integer userId, ItemDto itemDto) {
        return new Item(
                itemDto.getId() != null ? itemDto.getId() : null,
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
