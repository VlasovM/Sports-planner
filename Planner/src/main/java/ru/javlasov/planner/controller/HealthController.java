package ru.javlasov.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.service.HealthService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

    private final HealthService healthService;


    @GetMapping
    public String health() {
        return "health";
    }

    @GetMapping("/create")
    public String createHealth() {
        return "createHealth";
    }

    @GetMapping("/edit/{id}")
    public String editHealth(@PathVariable(name = "id") Long id, Model model) {
        HealthDto healthDto = healthService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("date", healthDto.getDate());
        model.addAttribute("clinic", healthDto.getClinic());
        model.addAttribute("doctorSpecialization", healthDto.getDoctorSpecialization());
        model.addAttribute("doctorFullName", healthDto.getDoctorFullName());
        model.addAttribute("result", healthDto.getResult());
        return "editHealth";
    }

}
