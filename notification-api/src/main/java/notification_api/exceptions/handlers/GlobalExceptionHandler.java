package notification_api.exceptions.handlers;

import notification_api.exceptions.AbstractException;
import notification_api.exceptions.ResourceNotFound;
import notification_api.exceptions.ValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors (like duplicates)
    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<Map<String, Object>> handleValidateException(ValidateException exception) {
        return buildResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle resource not found errors
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFound exception) {
        return buildResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle any other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOtherExceptions(Exception exception) {
        return buildResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build JSON response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
