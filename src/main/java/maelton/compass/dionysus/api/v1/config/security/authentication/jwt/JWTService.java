package maelton.compass.dionysus.api.v1.config.security.authentication.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Value("${api.security.jwt.secret}")
    private String secret;

    public JSONWebTokenDTO generateToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> authenticationUserRoles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        try {
            ZonedDateTime tokenExpirationDateTime = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(2);

            String token = JWT.create()
                    .withIssuer("Dionysus Wine Boutique")
                    .withSubject(authentication.getName()) //user email
                    .withClaim("Roles", authenticationUserRoles)
                    .withExpiresAt(tokenExpirationDateTime.toInstant())
                    .sign(algorithm);

            return new JSONWebTokenDTO(
                    token,
                    "Bearer",
                    getTokenExpiration(tokenExpirationDateTime),
                    authentication.getName(),
                    authenticationUserRoles
            );
        } catch (JWTCreationException e) {
            throw new RuntimeException("JWT authentication failed: ", e);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("Dionysus Wine Boutique")
                    .build()
                    .verify(token)
                    .getSubject(); //user email
        } catch(JWTVerificationException e) {
            return null;
        }
    }

    private String getTokenExpiration(ZonedDateTime tokenExpirationDateTime) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z").format(tokenExpirationDateTime);
    }
}