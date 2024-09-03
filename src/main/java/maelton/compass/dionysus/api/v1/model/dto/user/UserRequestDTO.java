package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import maelton.compass.dionysus.api.v1.model.enums.UserRole;

import java.time.LocalDate;

@Valid
@Schema(description = "DTO for creation or update of user registries")
public record UserRequestDTO(
        @NotBlank(message = "Name field is mandatory")
        String name,

        @NotNull(message = "Birthdate field is mandatory")
        LocalDate birthDate,

        @NotBlank(message = "Email field is mandatory")
        @Email(message = "Email field must be a valid email address")
        String email,

        @NotBlank(message = "Password field is mandatory and cannot be blank")
        //TODO: Validate it
        @Size(min = 8, message = "Password field must have at least 8 characters")
        String password,

        @NotNull(message = "Role field is mandatory")
        UserRole role
) {}