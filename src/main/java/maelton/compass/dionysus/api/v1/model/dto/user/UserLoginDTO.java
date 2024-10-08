package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Valid
@Schema(description = "DTO for logging in users")
public record UserLoginDTO(
        @NotBlank(message = "Email field is mandatory")
        @Email(message = "Email field must be a valid email emailAddress")
        String email,

        @NotBlank(message = "Password field is mandatory")
        String password
) {}
