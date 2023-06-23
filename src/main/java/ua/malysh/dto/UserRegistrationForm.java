package ua.malysh.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationForm(
        @NotNull @NotBlank @Size(max = 20) String username,
        @NotNull @NotBlank @Email String email,
        @NotNull @NotBlank @Size(max = 45) String password
) {
}
