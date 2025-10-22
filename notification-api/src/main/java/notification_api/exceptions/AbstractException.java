package notification_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface AbstractException {
    Integer getErrorStatusCode();
    String getMessage();
}
