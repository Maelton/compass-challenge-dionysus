package maelton.compass.dionysus.api.v1.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import maelton.compass.dionysus.api.v1.model.enums.UserRole;

import java.time.LocalDate;
import java.util.UUID;

@Valid
@Schema(description = "DTO used to represent users data in responses")
public record UserResponseDTO(
        UUID id,
        String name,
        LocalDate birthDate,
        String email,
        UserRole role
) {}