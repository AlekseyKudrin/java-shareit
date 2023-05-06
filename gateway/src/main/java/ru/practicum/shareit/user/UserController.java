package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@Slf4j
@Controller
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {

    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object> create(
            @Validated(UserDto.Create.class)
            @RequestBody UserDto userDto
    ) {
        log.info("Creating user");
        return userClient.create(userDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> update(
            @PathVariable int userId,
            @Validated(UserDto.Update.class)
            @RequestBody UserDto userDto
    ) {
        log.info("Updating User");
        return userClient.update(userId, userDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> get(
            @PathVariable int userId
    ) {
        log.info("Return user by Id");
        return userClient.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(
            @PathVariable long userId
    ) {
        log.info("Delete user by id");
        return userClient.deleteUser(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Return user list");
        return userClient.getAll();
    }
}
