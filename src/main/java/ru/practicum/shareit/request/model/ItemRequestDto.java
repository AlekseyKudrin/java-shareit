package ru.practicum.shareit.request.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.model.ItemDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    public interface Create {
    }

    private Long id;

    private Long requestorid;

    @NotBlank(groups = Create.class)
    @Size(groups = Create.class, min = 1, max = 200)
    private String description;

    private LocalDateTime created;

    private List<ItemDto> items;
}
