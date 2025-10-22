package notification_api.dao.interfaces;

import notification_api.models.entity.TemplateEntity;

import java.util.Optional;

public interface TemplateDao {
    Optional<TemplateEntity> findByTenantIdAndName(String tenantId, String name);

    TemplateEntity save(TemplateEntity newTemplate);
}
