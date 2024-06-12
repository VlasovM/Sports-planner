package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.WorkoutDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.WorkoutMapper;
import ru.javlasov.sportsplanner.model.Workout;
import ru.javlasov.sportsplanner.repository.WorkoutRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;
import ru.javlasov.sportsplanner.service.WorkoutService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final UserCredentialsService userCredentialsService;

    private final WorkoutMapper workoutMapper;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutDto> getAllForCurrentUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser().getUser();
        List<Workout> findWorkout = workoutRepository.findAllByUser(currentUser);
        return workoutMapper.modelListToDtoList(findWorkout);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        workoutRepository.deleteById(id);
        sendMessage("Пользователь %s удалил тренировку с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void updateOrCreate(WorkoutDto workoutDto) {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var currentUser = currentUserCredentials.getUser();
        var workout = workoutMapper.dtoToModel(workoutDto);
        workout.setUser(currentUser);
        workout = workoutRepository.save(workout);
        sendMessage("Пользователь %s %s тренировку с id = %d".formatted(
                currentUserCredentials.getEmail(), workoutDto.getId() == null ? "создал" : "изменил",
                workout.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutDto getById(Long id) {
        var workout = findById(id);
        return workoutMapper.modelToDto(workout);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private Workout findById(Long workoutId) {
        return workoutRepository.findById(workoutId)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти тренировку по id %d".formatted(workoutId), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

}
