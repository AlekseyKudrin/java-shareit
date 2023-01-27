package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userDao.add(UserMapper.toUser(userDto));
        log.info("User successfully created");
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto update(int userId, UserDto userDto) {
        User user = userDao.update(userId, UserMapper.toUser(userDto));
        log.info("User successfully update");
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto get(int userId) {
        User user = userDao.get(userId);
        log.info("Return user by Id successfully");
        return UserMapper.toUserDto(user);
    }

    @Override
    public java.util.List<User> getAll() {
        List<User> users = userDao.getAll();
        log.info("Return user list successfully");
        return users;
    }

    @Override
    public Boolean delete(long userId) {
        Boolean isDelete = userDao.delete(userId);
        log.info("Delete user by id successfully");
        return isDelete;
    }
}
