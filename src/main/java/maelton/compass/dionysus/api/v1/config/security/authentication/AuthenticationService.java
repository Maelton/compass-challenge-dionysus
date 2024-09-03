package maelton.compass.dionysus.api.v1.config.security.authentication;

import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.JSONWebTokenDTO;
import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.JWTService;
import maelton.compass.dionysus.api.v1.exception.user.UserAuthenticationFailureException;
import maelton.compass.dionysus.api.v1.model.dto.user.UserLoginDTO;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    public AuthenticationService(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    //AUTHENTICATION
    public JSONWebTokenDTO authenticateUser(UserLoginDTO userLoginDTO) {
        //Spring Security itself authenticates the received login data for me
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.email(),
                            userLoginDTO.password()
                    )
            );

            return jwtService.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new UserAuthenticationFailureException("Email or password incorrect!");
        }
    }
}
