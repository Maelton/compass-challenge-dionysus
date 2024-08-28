package maelton.compass.dionysus.api.v1.exception.handler;

import maelton.compass.dionysus.api.v1.exception.wine_model.WineModelAlreadyExistsException;
import maelton.compass.dionysus.api.v1.exception.wine_model.WineModelIdNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WineModelExceptionHandler {
    @ExceptionHandler(WineModelAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleWineModelAlreadyExistsException(WineModelAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(HttpStatus.CONFLICT, e.getMessage()));
    }

    @ExceptionHandler(WineModelIdNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleWineModelIdNotFoundException(WineModelIdNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}
