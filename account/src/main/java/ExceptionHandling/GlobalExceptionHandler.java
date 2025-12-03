package ExceptionHandling;

import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("NOT_FOUND", ex.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(io.micrometer.core.instrument.config.validate.ValidationException.class)
        public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("VALIDATION_ERROR", ex.getMessage(), LocalDateTime.now()));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleAll(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("INTERNAL_ERROR", "Unexpected error", LocalDateTime.now()));
        }
    }

    record ErrorResponse(String code, String message, LocalDateTime timestamp) {}

