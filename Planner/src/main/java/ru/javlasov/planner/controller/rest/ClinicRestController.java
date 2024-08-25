package ru.javlasov.planner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.dto.ModerationClinicRequestDto;
import ru.javlasov.planner.enums.Status;
import ru.javlasov.planner.request.IncomeClinicRequest;
import ru.javlasov.planner.response.AsyncClinicResponse;
import ru.javlasov.planner.service.ClinicService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinic")
@Slf4j
public class ClinicRestController {

    private final ClinicService clinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AsyncClinicResponse receivedClinicInformation(
            @RequestParam(name = "requestId") String requestId,
            @Valid @RequestBody IncomeClinicRequest incomeClinicRequest) {
        return clinicService.processIncomeClinicRequest(incomeClinicRequest, requestId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ModerationClinicRequestDto>> getClinicRequestsForModerator() {
        var resultList = clinicService.findRequestsForModerator(Status.MODERATION);
        return ResponseEntity.ok(resultList);
    }

    @PostMapping("/chooseRequest")
    public ResponseEntity<?> chooseRequest(@RequestParam(name = "requestId") String requestId,
                                           @RequestParam(name = "userId") Long userId) {
        clinicService.chooseUserForRequestByModerator(requestId, userId);
        return ResponseEntity.ok().build();
    }

}
