package ru.javlasov.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.javlasov.clinic.dto.ClinicRequest;
import ru.javlasov.clinic.dto.HealthInformationDto;
import ru.javlasov.clinic.dto.PatientDto;
import ru.javlasov.clinic.model.Doctor;
import ru.javlasov.clinic.model.HealthInformation;
import ru.javlasov.clinic.repository.HealthInformationRepository;
import ru.javlasov.clinic.service.ClinicService;
import ru.javlasov.clinic.service.UserCredentialsService;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final UserCredentialsService userCredentialsService;

    private final HealthInformationRepository healthInformationRepository;

    @Override
    public void addAndSendPatientInformation(HealthInformationDto healthInformationDto) {
        var currentDoctor = userCredentialsService.getCurrentAuthUser().getDoctor();
        createAndSaveHealthInformation(healthInformationDto, currentDoctor);
        sendClinicRequestToSportsPlanner(healthInformationDto, currentDoctor);
    }

    private void createAndSaveHealthInformation(HealthInformationDto healthInformationDto, Doctor currentDoctor) {
        var healthInformation = new HealthInformation();
        healthInformation.setPatientName(healthInformationDto.getPatientName());
        healthInformation.setPatientMiddleName(healthInformation.getPatientMiddleName());
        healthInformation.setPatientSurname(healthInformation.getPatientSurname());
        healthInformation.setPatientBirthday(healthInformationDto.getPatientBirthday());
        healthInformation.setDoctor(currentDoctor);
        healthInformation.setVisited(healthInformationDto.getVisited());
        healthInformation.setResult(healthInformationDto.getResult());
        healthInformationRepository.save(healthInformation);
    }

    private void sendClinicRequestToSportsPlanner(HealthInformationDto healthInformationDto, Doctor currentDoctor) {
        var clinicRequest = new ClinicRequest();
        var patient = new PatientDto();
        patient.setName(healthInformationDto.getPatientName());
        patient.setMiddleName(healthInformationDto.getPatientMiddleName());
        patient.setSurname(healthInformationDto.getPatientSurname());
        patient.setBirthday(healthInformationDto.getPatientBirthday());
        clinicRequest.setPatient(patient);
        clinicRequest.setVisitDate(healthInformationDto.getVisited());
        clinicRequest.setResult(healthInformationDto.getResult());
        clinicRequest.setDoctorFullName(currentDoctor.getFullName());
        clinicRequest.setDoctorSpecialization(currentDoctor.getSpecialization().getTitle());

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/v1/clinic";
        HttpEntity<ClinicRequest> response = new HttpEntity<>(clinicRequest);
        restTemplate.exchange(url, HttpMethod.POST, response, ClinicRequest.class);

    }

}
