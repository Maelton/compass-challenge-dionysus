package maelton.compass.dionysus.api.v1.exception.handler;

import maelton.compass.dionysus.api.v1.exception.user.UserEmailNotFoundException;
import maelton.compass.dionysus.api.v1.exception.user.UserUUIDNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

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
}
