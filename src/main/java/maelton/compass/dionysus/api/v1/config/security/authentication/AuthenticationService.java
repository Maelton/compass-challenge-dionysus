package maelton.compass.dionysus.api.v1.config.security.authentication;

import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.JSONWebTokenDTO;
import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.JWTService;
import maelton.compass.dionysus.api.v1.exception.user.UserAuthenticationFailureException;
import maelton.compass.dionysus.api.v1.exception.user.UserEmailNotFoundException;
import maelton.compass.dionysus.api.v1.exception.user.UserPasswordResetFailedException;
import maelton.compass.dionysus.api.v1.model.dto.user.UserLoginDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserPasswordResetDTO;
import maelton.compass.dionysus.api.v1.model.entity.User;
import maelton.compass.dionysus.api.v1.repository.UserRepository;
import maelton.compass.dionysus.api.v1.service.EmailService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JWTService jwtService,
                                 UserRepository userRepository,
                                 EmailService emailService,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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

            return jwtService.generateJSONWebToken(authentication);
        } catch (AuthenticationException e) {
            throw new UserAuthenticationFailureException("Email or password incorrect!");
        }
    }

    //PASSWORD RESET EMAIL
    public String sendResetPasswordEmail(String email) {
        if(userRepository.existsByEmail(email)) {
            String token = jwtService.generatePasswordResetToken(email);
            String passwordResetURL = "http://localhost:8080/v1/auth/resetPassword?token=" + token;
            emailService.send(email, "Password reset email - Dionysus Wine Boutique", passwordResetURL);
            return "Password reset email sent successfully!";
        }
        throw new UserEmailNotFoundException(email);
    }

    //PASSWORD RESET
    public String resetPassword(String passwordResetToken, UserPasswordResetDTO passwordResetDTO) {
        if(!passwordResetDTO.password().equals(passwordResetDTO.passwordConfirmation()))
            throw new UserPasswordResetFailedException("Passwords do not match!");

        User user = userRepository.findByEmail(jwtService.getUserEmailFromPasswordResetToken(passwordResetToken)).orElseThrow(
                () -> new UserPasswordResetFailedException("Token validation failed!")
        );
        user.setPassword(passwordEncoder.encode(passwordResetDTO.password()));
        userRepository.save(user);

        return "Password has been updated successfully!";
    }
}
