package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.javlasov.planner.dto.ClinicRequestDto;
import ru.javlasov.planner.enums.ClinicResponseStatus;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.mapper.ClinicRequestMapper;
import ru.javlasov.planner.model.ClinicRequest;
import ru.javlasov.planner.model.Health;
import ru.javlasov.planner.model.User;
import ru.javlasov.planner.repository.ClinicRequestRepository;
import ru.javlasov.planner.repository.HealthRepository;
import ru.javlasov.planner.repository.UserRepository;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;
import ru.javlasov.planner.response.ClinicResponse;
import ru.javlasov.planner.service.ClinicService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRequestRepository clinicRequestRepository;

    private final UserRepository userRepository;

    private final ClinicRequestMapper clinicRequestMapper;

    private final HealthRepository healthRepository;

    @Override
    @Transactional
    public AsyncClinicResponse processIncomeClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId) {
        var clinicRequestDto = fillClinicRequest(incomeClinicRequest, requestId);
        clinicRequestRepository.save(clinicRequestMapper.dtoToEntity(clinicRequestDto));
        return AsyncClinicResponse.getInstance();
    }

    @Override
    @Transactional
    public void processClinicRequest() {
        List<ClinicRequest> findClinicRequest = clinicRequestRepository.findAllByStatus(Status.PROCESS);
        findClinicRequest.forEach(this::processClinicRequest);
    }

    private void processClinicRequest(ClinicRequest clinicRequest) {
        boolean isValidDate = validateIncomeData(clinicRequest);
        var clinicResponse = new ClinicResponse();
        if (isValidDate) {
            List<User> usersForClinicRequest = userRepository
                    .findUsersForClinicRequest(clinicRequest.getPatientName(), clinicRequest.getPatientMiddleName(),
                            clinicRequest.getPatientSurname(), clinicRequest.getPatientBirthday());
            if (usersForClinicRequest.size() > 1) {
                //TODO: функционал доп. контроля от модератора
                clinicRequest.setStatus(Status.MODERATION);
            } else if (usersForClinicRequest.isEmpty()) {
                fillClinicResponse(clinicResponse, ClinicResponseStatus.USER_NOT_FOUND);
                fillAndUpdateClinicRequest(clinicRequest, ClinicResponseStatus.USER_NOT_FOUND);
                sendClinicResponse(clinicResponse);
            } else {
                fillClinicResponse(clinicResponse, ClinicResponseStatus.SUCCEED);
                fillAndSaveHeath(clinicRequest, usersForClinicRequest.get(0));
                sendClinicResponse(clinicResponse);
                clinicRequest.setStatus(Status.SUCCEED);
                clinicRequestRepository.save(clinicRequest);
            }
        } else {
            fillClinicResponse(clinicResponse, ClinicResponseStatus.NOT_VALID_DATA);
            fillAndUpdateClinicRequest(clinicRequest, ClinicResponseStatus.NOT_VALID_DATA);
            sendClinicResponse(clinicResponse);
        }
    }

    private void fillClinicResponse(ClinicResponse clinicResponse, ClinicResponseStatus clinicResponseStatus) {
        clinicResponse.setCode(clinicResponseStatus.getId());
        clinicResponse.setStatus(clinicResponse.getStatus());
        clinicResponse.setMessage(clinicResponse.getMessage());

    }

    private void fillAndUpdateClinicRequest(ClinicRequest clinicRequest, ClinicResponseStatus clinicResponseStatus) {
        clinicRequest.setStatus(clinicResponseStatus.getStatus());
        clinicRequest.setNote(clinicRequest.getNote());
        clinicRequestRepository.save(clinicRequest);
    }

    private void fillAndSaveHeath(ClinicRequest clinicRequest, User user) {
        var health = new Health();
        health.setClinic(clinicRequest.getClinic());
        health.setResult(clinicRequest.getResult());
        health.setDoctorFullName(clinicRequest.getDoctorFullName());
        health.setDoctorSpecialization(clinicRequest.getDoctorSpecialization());
        health.setDate(clinicRequest.getDateVisited());
        health.setUser(user);
        healthRepository.save(health);
    }

    private boolean validateIncomeData(ClinicRequest clinicRequest) {
        return clinicRequest.getDoctorFullName() != null && clinicRequest.getDoctorSpecialization() != null
                && clinicRequest.getDateVisited() != null && clinicRequest.getPatientName() != null &&
                clinicRequest.getPatientSurname() != null;
    }

    private ClinicRequestDto fillClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId) {
        var clinicRequestDto = new ClinicRequestDto();
        clinicRequestDto.setRequestId(requestId);
        clinicRequestDto.setDateVisited(incomeClinicRequest.getVisitDate());
        clinicRequestDto.setDoctorFullName(incomeClinicRequest.getDoctorFullName());
        clinicRequestDto.setDoctorSpecialization(incomeClinicRequest.getDoctorSpecialization());
        clinicRequestDto.setResult(incomeClinicRequest.getResult());
        clinicRequestDto.setStatus(Status.PROCESS);
        clinicRequestDto.setPatient(incomeClinicRequest.getPatient());
        return clinicRequestDto;
    }

    private void sendClinicResponse(ClinicResponse clinicResponse) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8091/api/v1/clinic/incomeResponse/";
        HttpEntity<ClinicResponse> response = new HttpEntity<>(clinicResponse);
        restTemplate.exchange(url, HttpMethod.POST, response, ClinicResponse.class);
    }

}
