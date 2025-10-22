package notification_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import notification_api.models.request.CreateTemplateRequestDto;
import notification_api.models.response.CreateTemplateResponse;
import notification_api.service.interfaces.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.Option;

@RestController
@RequestMapping("/api/template")
@RequiredArgsConstructor
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("/create")
    public ResponseEntity<CreateTemplateResponse> createTemplate(@Valid @RequestBody CreateTemplateRequestDto payload) {
        return ResponseEntity.status(HttpStatus.CREATED).body(templateService.createTemplate(payload));
    }
}
