package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public Optional<UserDto> create(UserDto userDto) {
        User user = userDao.add(UserMapper.toUser(userDto)).orElse(new User());
        return Optional.of(UserMapper.toUserDto(user));
    }

    @Override
    public Optional<UserDto> update(int userId, UserDto userDto) {
        User user = userDao.update(userId,UserMapper.toUser(userDto)).orElse(new User());
        return Optional.of(UserMapper.toUserDto(user));
    }

    @Override
    public Optional<User> get(int userId) {
        return userDao.get(userId);
    }

    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public Boolean delete(int userId) {
        return userDao.delete(userId);
    }
}
