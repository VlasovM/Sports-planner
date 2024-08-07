package ru.javlasov.clinic.service;

import ru.javlasov.clinic.dto.HealthInformationDto;

public interface ClinicService {

    void addAndSendPatientInformation(HealthInformationDto healthInformationDto);

}
