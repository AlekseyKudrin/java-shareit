package ru.practicum.shareit.user.dao.impl;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptionHandler.exception.ConflictDataException;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    private long id = 1;

    private final Map<Long, User> userStorage = new HashMap<>();

    @Override
    public User add(User user) {
        validation(user);
        user.setId(id);
        userStorage.put(id, user);
        id++;
        return user;
    }

    @Override
    public User get(long userId) {
        validation(userId);
        return userStorage.get(userId);
    }

    @Override
    public User update(long userId, User user) {
        validation(userId);
        validation(user);
        User userUpdate = userStorage.get(userId);
        if (user.getName() != null && !user.getName().isBlank()) {
            userUpdate.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userUpdate.setEmail(user.getEmail());
        }
        return get(userUpdate.getId());
    }

    @Override
    public Boolean delete(long userId) {
        validation(userId);
        userStorage.remove(userId);
        return true;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userStorage.values());
    }

    private void validation(User user) {
        for (User userTemp : userStorage.values()) {
            if (userTemp.getEmail().equals(user.getEmail())) {
                throw new ConflictDataException("This email is already taken by another user");
            }
        }
    }

    private void validation(long userId) {
        if (!userStorage.containsKey(userId)) {
            throw new ValidationFieldsException("User by ID not found");
        }
    }
}
