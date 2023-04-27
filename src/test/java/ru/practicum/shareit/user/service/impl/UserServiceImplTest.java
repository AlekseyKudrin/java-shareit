package ru.practicum.shareit.user.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.user.dao.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User(1L, "User1 name", "user1@mail.com", new HashSet<>());
    }

    @Test
    void create() {
        when(repository.save(any(User.class))).thenReturn(user);

        UserDto userDto = service.create(
                UserMapper.toUserDto(user));

        assertEquals(1, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    @Test
    void update() {
        when(repository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(user));
        when(repository.save(any(User.class)))
                .thenReturn(user);

        UserDto userDto = UserMapper.toUserDto(user);
        service.update(userDto.getId(), userDto);

        assertEquals(1, userDto.getId());
        assertEquals("User1 name", userDto.getName());
        assertEquals("user1@mail.com", userDto.getEmail());
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
        when(repository.findAll())
                .thenReturn(List.of(user));

        List<User> userDto = service.getAll();

        assertEquals(1, userDto.size());
        assertEquals(1, userDto.get(0).getId());
        assertEquals("User1 name", userDto.get(0).getName());
        assertEquals("user1@mail.com", userDto.get(0).getEmail());
    }

    @Test
    void delete() {
        Boolean delete = service.delete(user.getId());

        assertTrue(delete);
    }
}