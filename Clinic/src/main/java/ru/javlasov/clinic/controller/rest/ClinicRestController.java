package ru.javlasov.clinic.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.javlasov.clinic.dto.HealthInformationDto;

@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class ClinicRestController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/create")
    public void addClientInformation(@RequestBody HealthInformationDto healthInformationDto) {

    }

}
