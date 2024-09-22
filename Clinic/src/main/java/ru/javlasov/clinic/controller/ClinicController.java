package ru.javlasov.clinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clinic")
public class ClinicController {

    @GetMapping("/create")
    public String createUserInformation() {
        return "createUserInformation";
    }

}
