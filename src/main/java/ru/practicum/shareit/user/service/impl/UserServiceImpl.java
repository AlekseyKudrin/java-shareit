package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dao.UserDao;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import javax.validation.Valid;
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
        log.info("User successfully created");
        return Optional.of(UserMapper.toUserDto(user));
    }

    @Override
    public Optional<UserDto> update(int userId, UserDto userDto) {
        User user = userDao.update(userId,UserMapper.toUser(userDto)).orElse(new User());
        log.info("User successfully update");
        return Optional.of(UserMapper.toUserDto(user));
    }

    @Override
    public Optional<User> get(int userId) {
        Optional<User> user = userDao.get(userId);
        log.info("Return user by Id successfully");
        return user;
    }

    @Override
    public Collection<User> getAll() {
        Collection<User> users = userDao.getAll();
        log.info("Return user list successfully");
        return users;
    }

    @Override
    public Boolean delete(int userId) {
        Boolean isDelete = userDao.delete(userId);
        log.info("Delete user by id successfully");
        return isDelete;
    }
}
