package ru.practicum.shareit.user.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptionHandler.exception.ValidationFieldsException;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto update(long userId, UserDto userDto) {
        Optional<User> user = userRepository.findById(userId);
        User userUpdate = UserMapper.toUser(userDto);
        if (user.isPresent()) {
            if (userUpdate.getName() != null && !userUpdate.getName().isBlank()) {
                user.get().setName(userUpdate.getName());
            }
            if (userUpdate.getEmail() != null && !userUpdate.getEmail().isBlank()) {
                user.get().setEmail(userUpdate.getEmail());
            }
            return UserMapper.toUserDto(userRepository.save(user.get()));
        } else {
            throw new ValidationFieldsException("User by ID not found");
        }
    }

    @Override
    public UserDto get(long userId) {
        if (userRepository.findById(userId).isPresent()) {
            return UserMapper.toUserDto(userRepository.findById(userId).get());
        } else {
            throw new ValidationFieldsException("User by ID not found");
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean delete(long userId) {
        userRepository.deleteById(userId);
        return true;
    }
}
