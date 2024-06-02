package ru.javlasov.sportsplanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.service.TrainService;

@Controller
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @GetMapping()
    public String trains() {
        return "trains";
    }

    @GetMapping("/create")
    public String createTrain() {
        return "createTrain";
    }


    @GetMapping("/edit/{id}")
    public String editTrain(@PathVariable(name = "id") Long id, Model model) {
        TrainDto trainDto = trainService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("title", trainDto.getTitle());
        model.addAttribute("reflection", trainDto.getReflection());
        model.addAttribute("date", trainDto.getDate());
        return "editTrain";
    }

}
