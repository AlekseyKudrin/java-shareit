package ru.practicum.shareit.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserDto {
    Long id;
    @NotBlank(message = "Field name cannot be empty")
    String name;
    @Email(message = "Field email incorrect")
    @NotBlank(message = "Field email cannot be empty")
    String email;
}
