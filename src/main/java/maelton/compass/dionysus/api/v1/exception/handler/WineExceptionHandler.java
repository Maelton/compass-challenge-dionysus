package maelton.compass.dionysus.api.v1.exception.handler;

import maelton.compass.dionysus.api.v1.exception.wine.WineUUIDNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WineExceptionHandler {

    //UUID
    @ExceptionHandler(WineUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleWineUUIDNotFoundException(WineUUIDNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}
