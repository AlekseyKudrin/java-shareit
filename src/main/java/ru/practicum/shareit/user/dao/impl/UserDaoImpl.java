package ru.practicum.shareit.user.dao.impl;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.exceptionHandler.exception.ValidationException;
import ru.practicum.shareit.user.exceptionHandler.exception.ValidationFieldsException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDaoImpl implements UserDao {

    private int id = 1;

    private Map<Integer, User> userStorage= new HashMap<>();
    @Override
    public Optional<User> add(User user) {
        validation(user);
        user.setId(id);
        userStorage.put(id,user);
        id++;
        return Optional.of(user);
    }

    @Override
    public Optional<User> get(int userId) {
        validation(userId);
        return Optional.of(userStorage.get(userId));
    }

    @Override
    public Optional<User> update(int userId, User user) {
        validation(userId);
        validation(user);
        User u2 = userStorage.get(userId);
        if (user.getName() != null) {
            u2.setName(user.getName());
        }
        if (user.getEmail() != null) {
            u2.setEmail(user.getEmail());
        }
        return get(userId);
    }

    @Override
    public Boolean delete(int userId) {
        validation(userId);
        userStorage.remove(userId);
        return true;
    }

    @Override
    public Collection<User> getAll() {
        return userStorage.values();
    }

    private void validation (User user) {
        for (User userTemp : userStorage.values()) {
            if (userTemp.getEmail().equals(user.getEmail())) {
                throw new ValidationException("This email is already taken by another user");
            }
        }
    }
    private void validation (int userId) {
        if (!userStorage.containsKey(userId)) {
            throw new ValidationFieldsException("User not found");
        }
    }
}
