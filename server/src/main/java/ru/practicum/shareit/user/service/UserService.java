package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto create(UserDto userDto);

    UserDto update(long userId, UserDto userDto);

    UserDto get(long userId);

    List<User> getAll();

    Boolean delete(long userId);
}
