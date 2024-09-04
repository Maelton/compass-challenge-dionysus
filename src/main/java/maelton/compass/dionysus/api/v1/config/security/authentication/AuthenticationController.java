package maelton.compass.dionysus.api.v1.config.security.authentication;

import jakarta.validation.Valid;

import maelton.compass.dionysus.api.v1.config.security.authentication.jwt.JSONWebTokenDTO;
import maelton.compass.dionysus.api.v1.exception.handler.ExceptionResponse;
import maelton.compass.dionysus.api.v1.model.dto.user.UserEmailDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserLoginDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "API authentication endpoints")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    //AUTHENTICATION
    @Operation(summary = "Authenticates a user", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "User successfully authenticated",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = JSONWebTokenDTO.class)
                            )
                         }
            ),
            @ApiResponse(responseCode = "401",
                         description = "Authentication failed, email or password incorrect",
                         content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                         }
            )
        }
    )
    @PostMapping("/users")
    public ResponseEntity<JSONWebTokenDTO> authenticateUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authenticationService.authenticateUser(userLoginDTO));
    }

    //FORGOT PASSWORD
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody UserEmailDTO email){
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.sendResetPasswordEmail(email.address()));
    }

    //TODO: RESET PASSWORD
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String token){
        return "";
    }
}
