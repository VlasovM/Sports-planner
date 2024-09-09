package ru.javlasov.clinic.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.clinic.dto.HealthInformationDto;
import ru.javlasov.clinic.dto.PlannerResponse;
import ru.javlasov.clinic.enums.Status;
import ru.javlasov.clinic.service.ClinicService;
import ru.javlasov.clinic.service.UserCredentialsService;
import ru.javlasov.clinic.service.impl.ClinicServiceImpl;

@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
@RestController
public class ClinicRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private final ClinicService clinicService;

    private final UserCredentialsService userCredentialsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addClientInformation(@Valid @RequestBody HealthInformationDto healthInformationDto) {
        clinicService.addAndSendPatientInformation(healthInformationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/currentUser")
    public String getCurrentUser() {
        return userCredentialsService.getCurrentAuthUser().getDoctor().getFullName();
    }

    @PostMapping("/incomeResponse")
    public ResponseEntity<PlannerResponse> getInformationFromSportsPlanner(
            @RequestParam(name = "responseId") String responseId,
            @RequestBody PlannerResponse plannerResponse) {
        var infoMessage = String.format("Получен ответ от Приложения \"Планнер\" на ранее отправленный запрос " +
                "с requestId = %s, Ответ: %s", responseId, plannerResponse);
        LOGGER.info(infoMessage);
        var responseForPlanner = new PlannerResponse(Status.SUCCEED, infoMessage);
        return ResponseEntity.ok(responseForPlanner);
    }

}
