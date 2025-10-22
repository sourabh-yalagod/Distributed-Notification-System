package notification_api.models.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collation = "template")
public class TemplateEntity extends AbstractModel {
    private UUID id;
    private String name;
    private Map<String, String> map = new HashMap<>();
    private String messageTemplate;
    private UUID tenantId;
}
