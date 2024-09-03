package maelton.compass.dionysus.api.v1.exception.handler;

import maelton.compass.dionysus.api.v1.exception.sale.SaleUUIDNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SaleExceptionHandler {

    //UUID
    @ExceptionHandler(SaleUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleSaleUUIDNotFoundException(SaleUUIDNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}
