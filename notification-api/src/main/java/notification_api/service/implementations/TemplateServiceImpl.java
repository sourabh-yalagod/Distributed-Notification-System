package notification_api.service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_api.dao.interfaces.TemplateDao;
import notification_api.dao.repositories.TemplateRepository;
import notification_api.exceptions.ResourceNotFound;
import notification_api.exceptions.ValidateException;
import notification_api.models.context.NotificationContext;
import notification_api.models.context.NotificationContextHolder;
import notification_api.models.entity.TemplateEntity;
import notification_api.models.request.CreateTemplateRequestDto;
import notification_api.models.response.CreateTemplateResponse;
import notification_api.service.interfaces.TemplateService;
import notification_api.utils.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class TemplateServiceImpl implements TemplateService {
    private final TemplateDao templateDao;
    private final TemplateRepository templateRepository;

    @Override
    public CreateTemplateResponse createTemplate(CreateTemplateRequestDto payload) {
        NotificationContext notificationContext = NotificationContextHolder.getNotificationContext();
        System.out.println("notificationContext : " + notificationContext);
        Optional<TemplateEntity> templateEntity = templateDao.findByTenantIdAndName(notificationContext.tenantId(), payload.getName());
        if (templateEntity.isPresent()) {
            throw new ValidateException("Template already exist with name : " + payload.getName());
        }
        TemplateEntity newTemplate = TemplateEntity.builder().id(CommonUtils.generateUUID()).name(payload.getName()).tenantId(notificationContext.tenantId()).build();
        newTemplate = templateDao.save(newTemplate);
        if (newTemplate == null) {
            throw new ResourceNotFound("Template creation failed....!");
        }
        return new CreateTemplateResponse(newTemplate.getId(), newTemplate.getName());
    }
}
