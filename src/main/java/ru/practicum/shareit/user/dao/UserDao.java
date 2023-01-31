package ru.practicum.shareit.user.dao;

import ru.practicum.shareit.user.model.User;

import java.util.List;


public interface UserDao {
    User add(User user);

    User get(long userId);

    User update(long userId, User user);

    Boolean delete(long userId);

    List<User> getAll();
}
