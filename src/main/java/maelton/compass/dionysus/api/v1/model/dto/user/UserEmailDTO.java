package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Valid
@Schema(description = "DTO used to represent a user email data during a forgot password request")
public record UserEmailDTO(
        @NotBlank(message = "Email address field is mandatory")
        @Email(message = "Email address field must be valid")
        String emailAddress
) {}
