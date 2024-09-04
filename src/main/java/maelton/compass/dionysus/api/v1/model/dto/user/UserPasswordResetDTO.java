package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Valid
@Schema(description = "DTO used to represent a new user password data during a password reset request")
public record UserPasswordResetDTO(
        @NotBlank(message = "Password field is mandatory")
        String password,

        @NotBlank(message = "Password confirmation field is mandatory")
        String passwordConfirmation
) {}
