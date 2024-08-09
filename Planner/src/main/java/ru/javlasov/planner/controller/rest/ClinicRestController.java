package ru.javlasov.planner.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@Slf4j
public class ClinicRestController {

    private final ClinicService clinicService;

//    @PostMapping
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AsyncClinicResponse receivedClinicInformation(
            @RequestParam(name = "requestId") String requestId,
            @RequestBody IncomeClinicRequest incomeClinicRequest) {
        System.out.println("Income requestId: " + requestId);
        AsyncClinicResponse testResponse = new AsyncClinicResponse();
        testResponse.setResponseStatus("SUCCEED");
        return testResponse;
//        return clinicService.processIncomeClinicRequest(incomeRequest, requestId);
    }

}
