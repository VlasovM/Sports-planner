package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ru.javlasov.planner.dto.ClinicRequestDto;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.dto.ModerationClinicRequestDto;
import ru.javlasov.planner.dto.UserDto;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.ClinicRequestMapper;
import ru.javlasov.planner.mapper.UserMapper;
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
import ru.javlasov.planner.service.LoggingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicServiceImpl implements ClinicService {

    private static final String SUCCEED_MESSAGE = "Сообщение успешно принято системой \"Планировщик\" в ";

    private static final String URL_CLINIC = "http://localhost:8091/api/v1/clinic";

    private static final Logger LOGGER = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private final LoggingService loggingService;

    private final ClinicRequestRepository clinicRequestRepository;

    private final UserRepository userRepository;

    private final ClinicRequestMapper clinicRequestMapper;

    private final HealthRepository healthRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional
    public AsyncClinicResponse processIncomeClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId) {
        String infoMessageForReceived = String.format("Получено сообщение от приложения \"Клиника\", " +
                "requestId = %s", requestId);
        createAndSendLogInformation(infoMessageForReceived, TypeMessage.INFO);
        var clinicRequestDto = fillClinicRequest(incomeClinicRequest, requestId);
        ClinicRequest save = clinicRequestRepository.save(clinicRequestMapper.dtoToEntity(clinicRequestDto));
        String infoSucceedMessage = String.format("Полученная информация от приложения \"Клиника\" сохранена " +
                "в базу данных с Id = %d", save.getId());
        createAndSendLogInformation(infoSucceedMessage, TypeMessage.INFO);
        return new AsyncClinicResponse(HttpStatus.OK, SUCCEED_MESSAGE + LocalDateTime.now());
    }

    @Override
    @Transactional
    public void processClinicRequest() {
        List<ClinicRequest> findClinicRequest = clinicRequestRepository.findAllByStatus(Status.PROCESS.getId());
        findClinicRequest.forEach(this::processClinicRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModerationClinicRequestDto> findRequestsForModerator(Status status) {
        List<ModerationClinicRequestDto> resultList = new ArrayList<>();
        List<ClinicRequest> allClinicRequestsForModeration = clinicRequestRepository.findAllByStatus(status.getId());
        for (ClinicRequest request : allClinicRequestsForModeration) {
            List<User> usersForClinicRequest = userRepository
                    .findUsersForClinicRequest(request.getPatientName(), request.getPatientMiddleName(),
                            request.getPatientSurname(), request.getPatientBirthday());
            List<UserDto> usersDtoForClinicRequest = userMapper.modelListToDtoList(usersForClinicRequest);
            var moderationClinicRequestDto = new ModerationClinicRequestDto(request.getRequestId(),
                    request.getDateVisited(), request.getDoctorFullName(), request.getDoctorSpecialization(),
                    request.getClinic(), usersDtoForClinicRequest);
            resultList.add(moderationClinicRequestDto);
        }
        return resultList;
    }

    @Override
    @Transactional
    public void chooseUserForRequestByModerator(String requestId, Long userId) {
        var clinicRequest = clinicRequestRepository.findByRequestId(requestId).orElseThrow();
        clinicRequest.setUserId(userId);
        clinicRequestRepository.save(clinicRequest);
    }

    private void createAndSendLogInformation(String logMessage, TypeMessage typeMessage) {
        LOGGER.info(logMessage);
        var loggingDto = new LoggerEvent(logMessage, typeMessage);
        loggingService.sendMessage(loggingDto);
    }

    private ClinicRequestDto fillClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId) {
        var clinicRequestDto = new ClinicRequestDto();
        clinicRequestDto.setRequestId(requestId);
        clinicRequestDto.setDateVisited(incomeClinicRequest.getVisitDate());
        clinicRequestDto.setDoctorFullName(incomeClinicRequest.getDoctorFullName());
        clinicRequestDto.setDoctorSpecialization(incomeClinicRequest.getDoctorSpecialization());
        clinicRequestDto.setResult(incomeClinicRequest.getResult());
        clinicRequestDto.setStatus(Status.PROCESS.getId());
        clinicRequestDto.setPatient(incomeClinicRequest.getPatient());
        return clinicRequestDto;
    }

    private void processClinicRequest(ClinicRequest clinicRequest) {
        LOGGER.info(String.format("Беру в обработку clinicRequest с requestId = %s", clinicRequest.getRequestId()));
        var clinicResponse = new ClinicResponse();
        List<User> usersForClinicRequest = userRepository
                .findUsersForClinicRequest(clinicRequest.getPatientName(), clinicRequest.getPatientMiddleName(),
                        clinicRequest.getPatientSurname(), clinicRequest.getPatientBirthday());
        if (clinicRequest.getUserId() != null) {
            processPreviouslyVerified(clinicRequest, clinicResponse);
        } else {
            processForFirstTime(usersForClinicRequest, clinicRequest, clinicResponse);
        }
    }

    private void processPreviouslyVerified(ClinicRequest clinicRequest, ClinicResponse clinicResponse) {
        var correctUser = userRepository.findById(clinicRequest.getUserId()).orElseThrow(() -> {
            String errorMessage = String.format("Не удалось найти юзера с id = %d", clinicRequest.getUserId());
            createAndSendLogInformation(errorMessage, TypeMessage.ERROR);
            throw new NotFoundException(errorMessage);
        });
        fillAndSaveHeath(clinicRequest, correctUser);
        String succeedMessage = String.format("Сообщение успешно обработано в %s", LocalDateTime.now());
        clinicResponse.setMessage(succeedMessage);
        clinicResponse.setStatus(Status.SUCCEED);
        sendClinicResponse(clinicResponse, clinicRequest.getRequestId());
        clinicRequest.setStatus(Status.SUCCEED.getId());
        clinicRequestRepository.save(clinicRequest);
    }

    private void processForFirstTime(List<User> usersForClinicRequest, ClinicRequest clinicRequest,
                                     ClinicResponse clinicResponse) {
        if (usersForClinicRequest.size() > 1) {
            clinicRequest.setStatus(Status.MODERATION.getId());
        } else if (usersForClinicRequest.isEmpty()) {
            String errorMessage = String.format("Не найден пользователь в системе приложения \"Планер\"" +
                    " для запроса с requestId = %s", clinicRequest.getRequestId());
            createAndSendLogInformation(errorMessage, TypeMessage.ERROR);
            clinicResponse.setStatus(Status.ERROR);
            clinicResponse.setMessage(errorMessage);
            clinicRequest.setNote(errorMessage);
            fillAndUpdateClinicRequest(clinicRequest, Status.ERROR);
            sendClinicResponse(clinicResponse, clinicRequest.getRequestId());
        } else {
            fillAndSaveHeath(clinicRequest, usersForClinicRequest.get(0));
            fillAndUpdateClinicRequest(clinicRequest, Status.SUCCEED);
            sendClinicResponse(clinicResponse, clinicRequest.getRequestId());
        }
    }

    private void fillAndUpdateClinicRequest(ClinicRequest clinicRequest, Status status) {
        clinicRequest.setStatus(status.getId());
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
        var newHealthInformation = healthRepository.save(health);
        LOGGER.info(String.format("Сохранили информацию о проверку здоровья спортсмена с id = %d",
                newHealthInformation.getId()));
    }

    private void sendClinicResponse(ClinicResponse clinicResponse, String requestId) {
        String infoMessageForClinicResponse = String.format("Отправляем ответ в приложение \"Клиника\"," +
                " ранее полученный с requestId = %s", requestId);
        LOGGER.info(infoMessageForClinicResponse);
        String headerForUrl = "?responseId=" + requestId;
        RestTemplate restTemplate = new RestTemplate();
        String url = URL_CLINIC + "/incomeResponse" + headerForUrl;
        HttpEntity<ClinicResponse> response = new HttpEntity<>(clinicResponse);
        try {
            ClinicResponse incomeClinicResponse = restTemplate.postForObject(url, response, ClinicResponse.class);
            LOGGER.info(url);
            LOGGER.info(String.format("Получен ответ от приложения \"Клиника\" по полученному запросу" +
                    " с requestId = %s, %s", requestId, incomeClinicResponse));
        } catch (Exception exception) {
            String errorMessage = String.format("Ошибка при отправке информации в приложениея \"Клиника\": %s",
                    exception.getMessage());
            createAndSendLogInformation(errorMessage, TypeMessage.ERROR);
        }
    }

}
