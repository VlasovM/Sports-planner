package ru.javlasov.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.javlasov.clinic.api.response.AsyncPlannerResponse;
import ru.javlasov.clinic.api.request.ClinicRequest;
import ru.javlasov.clinic.dto.HealthInformationDto;
import ru.javlasov.clinic.dto.PatientDto;
import ru.javlasov.clinic.enums.Specialization;
import ru.javlasov.clinic.model.Doctor;
import ru.javlasov.clinic.model.HealthInformation;
import ru.javlasov.clinic.repository.HealthInformationRepository;
import ru.javlasov.clinic.service.ClinicService;
import ru.javlasov.clinic.service.UserCredentialsService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private static final String URL = "http://localhost:8080/api/v1/clinic";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private final UserCredentialsService userCredentialsService;

    private final HealthInformationRepository healthInformationRepository;

    @Override
    public void addAndSendPatientInformation(HealthInformationDto healthInformationDto) {
        var currentDoctor = userCredentialsService.getCurrentAuthUser().getDoctor();
        var heathInformation = createAndSaveHealthInformation(healthInformationDto, currentDoctor);
        sendClinicRequestToSportsPlanner(heathInformation, currentDoctor);
    }

    private HealthInformation createAndSaveHealthInformation(HealthInformationDto healthInformationDto,
                                                             Doctor currentDoctor) {
        var healthInformation = fillHealthInformation(healthInformationDto, currentDoctor);
        var logMessage = String.format("Сохраняем полученную информацию о пациенте %s от доктора %s" +
                        " с requestId = %s ", healthInformation.getPatientSurname() + " "
                        + healthInformation.getPatientName(), currentDoctor.getFullName(),
                healthInformation.getRequestId());
        LOGGER.info(logMessage);
        return healthInformationRepository.save(healthInformation);
    }

    private HealthInformation fillHealthInformation(HealthInformationDto healthInformationDto, Doctor currentDoctor) {
        var healthInformation = new HealthInformation();
        healthInformation.setPatientName(healthInformationDto.getPatientName());
        healthInformation.setPatientMiddleName(healthInformationDto.getPatientMiddleName());
        healthInformation.setPatientSurname(healthInformationDto.getPatientSurname());
        healthInformation.setPatientBirthday(healthInformationDto.getPatientBirthday());
        healthInformation.setDoctorId(currentDoctor.getId());
        healthInformation.setVisited(healthInformationDto.getVisited());
        healthInformation.setResult(healthInformationDto.getResult());
        healthInformation.setRequestId(UUID.randomUUID().toString());
        return healthInformation;
    }

    private void sendClinicRequestToSportsPlanner(HealthInformation healthInformation, Doctor currentDoctor) {
        var clinicRequest = fillClinicRequest(healthInformation, currentDoctor);
        String headerForUrl = "?requestId=" + healthInformation.getRequestId();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ClinicRequest> request = new HttpEntity<>(clinicRequest);
        try {
            AsyncPlannerResponse asyncPlannerResponse = restTemplate
                    .postForObject(URL + headerForUrl, request, AsyncPlannerResponse.class);
            String logMessage = String.format("Получен ответ от приложения \"Планнер\". %s",
                    asyncPlannerResponse);
            LOGGER.info(logMessage);
        } catch (Exception exception) {
            String errorMessage = String.format("Ошибка при отправке информации в приложение \"Планнер\" %s",
                    exception.getMessage());
            LOGGER.info(errorMessage);
        }
    }

    private ClinicRequest fillClinicRequest(HealthInformation healthInformation, Doctor currentDoctor) {
        var clinicRequest = new ClinicRequest();
        var patient = new PatientDto();
        patient.setName(healthInformation.getPatientName());
        patient.setMiddleName(healthInformation.getPatientMiddleName());
        patient.setSurname(healthInformation.getPatientSurname());
        patient.setBirthday(healthInformation.getPatientBirthday());
        clinicRequest.setPatient(patient);
        clinicRequest.setVisitDate(healthInformation.getVisited());
        clinicRequest.setResult(healthInformation.getResult());
        clinicRequest.setDoctorFullName(currentDoctor.getFullName());
        var doctorSpecialization = Specialization.findById(currentDoctor.getSpecialization().getId());
        clinicRequest.setDoctorSpecialization(doctorSpecialization.getTitle());
        return clinicRequest;
    }

}
