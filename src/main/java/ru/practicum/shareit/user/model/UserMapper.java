package ru.practicum.shareit.user.model;

import javax.validation.Valid;

public class UserMapper {

    public static User toUser (UserDto userDto) {
        return new User(
                -1,
                userDto.getName() != null ? userDto.getName() : null,
                userDto.getEmail() != null ? userDto.getEmail() : null
        );
    }
    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
