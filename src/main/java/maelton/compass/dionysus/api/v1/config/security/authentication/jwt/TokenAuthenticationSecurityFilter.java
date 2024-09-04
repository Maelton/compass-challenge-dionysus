package maelton.compass.dionysus.api.v1.config.security.authentication.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import maelton.compass.dionysus.api.v1.exception.user.UserEmailNotFoundException;
import maelton.compass.dionysus.api.v1.model.entity.User;
import maelton.compass.dionysus.api.v1.repository.UserRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenAuthenticationSecurityFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserRepository userRepository;
    public TokenAuthenticationSecurityFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, UserEmailNotFoundException {

        String authenticationUserEmail = jwtService.validateJSONWebToken(this.getToken(request));
        try {
            if (authenticationUserEmail != null) {
                User authenticationUser = userRepository.findByEmail(authenticationUserEmail).orElseThrow(
                        () -> new UserEmailNotFoundException(authenticationUserEmail)
                );
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        authenticationUserEmail,
                        null,
                        authenticationUser.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (UserEmailNotFoundException e) {
            filterChain.doFilter(request, response);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String getToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer "))
            return authenticationHeader.replace("Bearer ", "");
        return null;
    }
}

