package ru.javlasov.planner.service;

import org.springframework.scheduling.annotation.Scheduled;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;

public interface ClinicService {

    AsyncClinicResponse processIncomeClinicRequest(IncomeClinicRequest incomeClinicRequest, String requestId);

    @Scheduled()
    void processClinicRequest();

}
