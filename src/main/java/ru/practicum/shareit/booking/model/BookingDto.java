package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.cons.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@AllArgsConstructor
public class BookingDto {

    public interface Create{
    }
    Long id;
    @NotBlank(message = "Field start cannot be empty", groups = BookingDto.Create.class)
    LocalDateTime start;
    @NotBlank(message = "Field end cannot be empty", groups = BookingDto.Create.class)
    LocalDateTime end;
    @NotBlank(message = "Field itemId cannot be empty", groups = BookingDto.Create.class)
    Long itemId;
    Item item;
    User booker;
    BookingStatus status;
}
