package notification_api.exceptions.handlers;

import notification_api.exceptions.AbstractException;
import notification_api.exceptions.ResourceNotFound;
import notification_api.exceptions.ValidateException;
import notification_api.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Supplier;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<String> handleValidateException(ValidateException exception) {
        return genericExceptionHandler(exception, () ->
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(exception.getErrorMessage()));
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFound exception) {
        return genericExceptionHandler(exception, () ->
                ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(exception.getErrorMessage())
        );
    }

    public ResponseEntity<String> genericExceptionHandler(final AbstractException exception,
                                                          final Supplier<ResponseEntity<String>> supplier) {
        if (!CommonUtils.isEmpty(exception.getErrorStatusCode())) {
            return ResponseEntity.status(exception.getErrorStatusCode()).body(exception.getMessage());
        }
        return supplier.get();
    }
}
