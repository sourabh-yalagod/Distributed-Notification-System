package notification_api.exceptions;

import lombok.Getter;

public class ValidateException extends RuntimeException implements AbstractException {

    @Getter
    private Integer statusCode;


    @Getter
    private String errorMessage;

    public ValidateException(String message) {
        super(message);
    }

    public ValidateException(String message, Integer statusCode) {
        super(message);
        this.errorMessage = message;
        this.statusCode = statusCode;
    }

    @Override
    public Integer getErrorStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
