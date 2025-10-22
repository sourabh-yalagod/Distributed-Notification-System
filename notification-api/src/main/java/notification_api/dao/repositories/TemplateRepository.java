package notification_api.dao.repositories;

import notification_api.models.entity.TemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TemplateRepository extends MongoRepository<TemplateEntity, UUID> {
    Optional<TemplateEntity> findByTenantIdAndName(String tenantId, String name);
}
