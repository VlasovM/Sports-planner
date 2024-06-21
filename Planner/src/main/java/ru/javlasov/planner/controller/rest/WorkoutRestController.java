package ru.javlasov.planner.controller.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.javlasov.planner.dto.WorkoutDto;
import ru.javlasov.planner.service.WorkoutService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workout")
public class WorkoutRestController {

    private final WorkoutService workoutService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WorkoutDto> getAllTrains() {
        return workoutService.getAllForCurrentUser();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrainById(@PathVariable("id") Long id) {
        workoutService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTrain(@Valid @RequestBody WorkoutDto workoutDto) {
        workoutService.updateOrCreate(workoutDto);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void editTrain(@Valid @RequestBody WorkoutDto workoutDto) {
        workoutService.updateOrCreate(workoutDto);
    }

}
