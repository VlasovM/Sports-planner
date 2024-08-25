package ru.javlasov.planner.service;

import org.springframework.scheduling.annotation.Scheduled;
import ru.javlasov.planner.dto.ModerationClinicRequestDto;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ClinicService {

    AsyncClinicResponse processIncomeClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId);

    List<ModerationClinicRequestDto> findRequestsForModerator(Status status);

    void chooseUserForRequestByModerator(String requestId, Long userId);

    @Scheduled(fixedRate = 120, timeUnit = TimeUnit.SECONDS)
    void processClinicRequest();

}
