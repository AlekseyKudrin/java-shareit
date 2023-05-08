package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(
            @Validated(UserDto.Create.class) @RequestBody UserDto userDto) {
        log.info("Creating user");
        return userService.create(userDto);
    }

    @PatchMapping("/{userId}")
    public UserDto update(
            @PathVariable int userId,
            @Validated(UserDto.Update.class) @RequestBody UserDto userDto) {
        log.info("Updating User");
        return userService.update(userId, userDto);
    }

    @GetMapping("/{userId}")
    public UserDto get(
            @PathVariable int userId) {
        log.info("Return user by Id");
        return userService.get(userId);
    }

    @DeleteMapping("/{userId}")
    public Boolean delete(
            @PathVariable long userId) {
        log.info("Delete user by id");
        return userService.delete(userId);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Return user list");
        return userService.getAll();
    }
}
