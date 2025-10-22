package notification_api.service.interfaces;

import notification_api.models.request.CreateTemplateRequestDto;
import notification_api.models.response.CreateTemplateResponse;

public interface TemplateService {
    CreateTemplateResponse createTemplate(CreateTemplateRequestDto payload);
}
