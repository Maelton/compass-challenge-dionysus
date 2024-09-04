package maelton.compass.dionysus.api.v1.exception.handler;

import maelton.compass.dionysus.api.v1.exception.user.UserAuthenticationFailureException;
import maelton.compass.dionysus.api.v1.exception.user.UserEmailNotFoundException;
import maelton.compass.dionysus.api.v1.exception.user.UserPasswordResetFailedException;
import maelton.compass.dionysus.api.v1.exception.user.UserUUIDNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    //AUTHENTICATION
    @ExceptionHandler(UserAuthenticationFailureException.class)
    public ResponseEntity<ExceptionResponse> handleUserAuthenticationFailure(UserAuthenticationFailureException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage()));
    }

    //EMAIL NOT FOUND
    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailNotFoundException(UserEmailNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    //UUID
    @ExceptionHandler(UserUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserUUIDNotFoundException(UserUUIDNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }

    //PASSWORD RESET
    @ExceptionHandler(UserPasswordResetFailedException.class)
    public ResponseEntity<ExceptionResponse> handleUserPasswordResetFailedException(UserPasswordResetFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
