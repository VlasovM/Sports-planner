package ru.javlasov.clinic.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.clinic.dto.HealthInformationDto;
import ru.javlasov.clinic.service.ClinicService;

@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
@RestController
public class ClinicRestController {

    private final ClinicService clinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addClientInformation(@RequestBody HealthInformationDto healthInformationDto) {
        clinicService.addAndSendPatientInformation(healthInformationDto);
    }

}
