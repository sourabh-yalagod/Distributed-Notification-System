package notification_api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CreateTemplateRequestDto {
    @NotBlank(message = "template name is required....!")
    private String name;

    private Map<String, String> map = new HashMap<>();

    @NotBlank(message = "message template is required....!")
    private String messageTemplate;
}
