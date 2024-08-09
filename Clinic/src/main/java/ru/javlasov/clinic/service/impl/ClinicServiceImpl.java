package ru.javlasov.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.javlasov.clinic.api.response.AsyncPlannerResponse;
import ru.javlasov.clinic.dto.ClinicRequest;
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

    private final UserCredentialsService userCredentialsService;

    private final HealthInformationRepository healthInformationRepository;

    @Override
    public void addAndSendPatientInformation(HealthInformationDto healthInformationDto) {
        var currentDoctor = userCredentialsService.getCurrentAuthUser().getDoctor();
        var heathInformation = createAndSaveHealthInformation(healthInformationDto, currentDoctor);
        sendClinicRequestToSportsPlanner(heathInformation, currentDoctor);
    }

    private HealthInformation createAndSaveHealthInformation(HealthInformationDto healthInformationDto, Doctor currentDoctor) {
        var healthInformation = new HealthInformation();
        healthInformation.setPatientName(healthInformationDto.getPatientName());
        healthInformation.setPatientMiddleName(healthInformationDto.getPatientMiddleName());
        healthInformation.setPatientSurname(healthInformationDto.getPatientSurname());
        healthInformation.setPatientBirthday(healthInformationDto.getPatientBirthday());
        healthInformation.setDoctor(currentDoctor);
        healthInformation.setVisited(healthInformationDto.getVisited());
        healthInformation.setResult(healthInformationDto.getResult());
        healthInformation.setRequestId(UUID.randomUUID().toString());
        return healthInformationRepository.save(healthInformation);
    }

    private void sendClinicRequestToSportsPlanner(HealthInformation healthInformation, Doctor currentDoctor) {
        var clinicRequest = fillClinicRequest(healthInformation, currentDoctor);
        String headerForUrl = "?requestId=" + healthInformation.getRequestId();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ClinicRequest> request = new HttpEntity<>(clinicRequest);
        AsyncPlannerResponse response = restTemplate.postForObject(URL + headerForUrl, request, AsyncPlannerResponse.class);
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
