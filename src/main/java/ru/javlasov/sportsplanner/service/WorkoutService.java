package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.WorkoutDto;

import java.util.List;

public interface WorkoutService {

    List<WorkoutDto> getAllForCurrentUser();

    void deleteById(Long id);

    void updateOrCreate(WorkoutDto workoutDto);

    WorkoutDto getById(Long id);

}
