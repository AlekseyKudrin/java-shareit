package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.model.enums.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookingDto {

    public interface Create {
    }

    Long id;
    @FutureOrPresent
    @NotNull(message = "Field start cannot be empty", groups = Create.class)
    LocalDateTime start;
    @Future
    @NotNull(message = "Field end cannot be empty", groups = Create.class)
    LocalDateTime end;
    @NotNull(message = "Field itemId cannot be empty", groups = Create.class)
    Long itemId;
    Item item;
    User booker;
    BookingStatus status;
}
