package ru.javlasov.planner.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;
import ru.javlasov.planner.service.ClinicService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
public class ClinicRestController {

    private final ClinicService clinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AsyncClinicResponse receivedClinicInformation(@RequestParam(name = "requestId") String requestId,
                                                         @RequestBody IncomeClinicRequest incomeRequest) {
        return clinicService.processIncomeClinicRequest(incomeRequest, requestId);
    }

}
