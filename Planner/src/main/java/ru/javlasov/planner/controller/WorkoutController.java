package ru.javlasov.planner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javlasov.planner.dto.WorkoutDto;
import ru.javlasov.planner.service.WorkoutService;

@Controller
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping()
    public String trains() {
        return "workout/workout";
    }

    @GetMapping("/create")
    public String createTrain() {
        return "workout/createWorkout";
    }


    @GetMapping("/edit/{id}")
    public String editTrain(@PathVariable(name = "id") Long id, Model model) {
        WorkoutDto workoutDto = workoutService.getById(id);
        model.addAttribute("id", id);
        model.addAttribute("title", workoutDto.getTitle());
        model.addAttribute("reflection", workoutDto.getReflection());
        model.addAttribute("date", workoutDto.getDate());
        return "workout/editWorkout";
    }

}
