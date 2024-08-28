package maelton.compass.dionysus.api.v1.exception.handler;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatusCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@Schema(description = "Represents exception messages")
public class ExceptionResponse {
    private final HttpStatusCode status;
    private final String messages;

    public ExceptionResponse(MethodArgumentNotValidException e) {
        this.status = e.getStatusCode();
        this.messages = getErrors(e);
    }

    public String getErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );
        return errors.toString();
    }
}
