package ru.practicum.shareit.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserDto {
    public interface Create {
    }

    public interface Update {
    }

    Long id;

    String name;

    String email;
}
