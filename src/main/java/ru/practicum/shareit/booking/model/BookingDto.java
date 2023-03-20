package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.cons.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookingDto {

    public interface Create{
    }
    Long id;
    @NotNull(message = "Field start cannot be empty", groups = Create.class)
    LocalDateTime start;
    @NotNull(message = "Field end cannot be empty", groups = Create.class)
    LocalDateTime end;
    @NotNull(message = "Field itemId cannot be empty", groups = Create.class)
    Long itemId;
    Item item;
    User booker;
    BookingStatus status;
}
