package ua.malysh.mapper;

import ua.malysh.domain.user.User;
import ua.malysh.dto.UserRegistrationForm;

import java.util.UUID;

public class UserMapper {

    private UserMapper() {}

    public static User toUser(UserRegistrationForm userRegistrationForm) {
        var user = new User();
        user.setIdentifier(UUID.randomUUID());
        user.setUsername(userRegistrationForm.username());
        user.setEmail(userRegistrationForm.email());
        user.setPassword(userRegistrationForm.password());
        return user;
    }
}
