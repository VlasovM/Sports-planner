package ru.javlasov.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String health() {
        return "health";
    }

    @GetMapping("/{id}")
    public String healthById(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("id", id);
        return "heathById";
    }

}
