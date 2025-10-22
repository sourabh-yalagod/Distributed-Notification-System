package notification_api.dao.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_api.dao.interfaces.TemplateDao;
import notification_api.dao.repositories.TemplateRepository;
import notification_api.models.entity.TemplateEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {
    private final TemplateRepository templateRepository;

    @Override
    public Optional<TemplateEntity> findByTenantIdAndName(final String tenantId, final String name) {
        return templateRepository.findByTenantIdAndName(tenantId, name);
    }

    @Override
    public TemplateEntity save(TemplateEntity newTemplate) {
        return templateRepository.save(newTemplate);
    }
}
