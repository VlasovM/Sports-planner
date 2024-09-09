package ru.javlasov.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clinic")
public class ClinicController {

    @GetMapping
    public String getAllRequestsForModerator() {
        return "clinic/allClinicRequests";
    }

}
