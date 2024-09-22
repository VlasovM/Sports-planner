package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.javlasov.planner.service.ClinicService;
import ru.javlasov.planner.service.DelayTaskService;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class DelayTaskServiceImpl implements DelayTaskService {

    private final ClinicService clinicService;

    @Scheduled(fixedRate = 120, timeUnit = TimeUnit.SECONDS)
    public void scheduleClinicRequests() {
        clinicService.processClinicRequest();
    }

}
