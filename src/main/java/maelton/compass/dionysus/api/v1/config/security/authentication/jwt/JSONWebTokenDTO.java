package maelton.compass.dionysus.api.v1.config.security.authentication.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO representing a JSON Web Token (JWT) response after successful authentication.")
public record JSONWebTokenDTO(
        String accessToken,
        String tokenType,
        String expiresAt,
        String userEmail,
        List<String> userRoles
) {}
