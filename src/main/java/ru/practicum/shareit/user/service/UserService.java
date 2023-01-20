package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    Optional<UserDto> create(UserDto userDto);

    Optional<UserDto> update(int userId, UserDto userDto);

    Optional<User> get(int userId);

    Collection<User> getAll();

    Boolean delete(int userId);
}
