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
    @NotBlank(message = "Field name cannot be empty", groups = Create.class)
    String name;
    @Email(message = "Field email incorrect", groups = {Create.class, Update.class})
    @NotBlank(message = "Field email cannot be empty", groups = Create.class)
    String email;
    public interface Create {
    }
    public interface Update {
    }
}
