package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {
    Optional<User> add(User user);

    Optional<User> get(int userId);

    Optional<User> update(int userId, User user);

    Boolean delete(int userId);

    Collection<User> getAll();
}
