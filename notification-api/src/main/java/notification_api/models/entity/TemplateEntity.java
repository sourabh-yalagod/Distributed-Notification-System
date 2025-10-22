package notification_api.models.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "template")
@Builder
public class TemplateEntity {

    @Id
    private String id;  // Optional: Use String if you face issues with UUID

    private String name;
    private Map<String, String> map = new HashMap<>();
    private String messageTemplate;
    private String tenantId;
}
