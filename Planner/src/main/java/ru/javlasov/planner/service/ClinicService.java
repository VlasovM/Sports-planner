package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.ModerationClinicRequestDto;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;

import java.util.List;

public interface ClinicService {

    AsyncClinicResponse processIncomeClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId);

    List<ModerationClinicRequestDto> findRequestsForModerator(Status status);

    void chooseUserForRequestByModerator(String requestId, Long userId);

    void processClinicRequest();

}
