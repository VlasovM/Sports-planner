package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.model.Workout;
import ru.javlasov.sportsplanner.repository.WorkoutRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TrainService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final WorkoutRepository workoutRepository;

    private final UserCredentialsService userCredentialsService;

//    private final TrainMapper trainMapper;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public List<TrainDto> getAllTrainsCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        //TODO
//        var trainsUser = currentUserCredentials.getUser().getTrains();
//        return trainMapper.modelSetToDtoList(trainsUser);
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        workoutRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти тренировку по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        workoutRepository.deleteById(id);
        sendMessage("Пользователь %s удалил тренировку с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void createOrEdit(TrainDto trainDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        trainDto.setUser(currentUser.getUser().getId());
//        var trainEntity = trainMapper.dtoToModel(trainDto);
//        checkReflection(trainEntity);
//        var trainAfterSave = trainRepository.save(trainEntity);
//        sendMessage("Пользователь %s %s тренировку с id = %d".formatted(
//                currentUser.getEmail(), trainDto.getId() == null ? "создал" : "изменил",
//                trainAfterSave.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainDto getById(Long id) {
        var train = workoutRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти тренировку по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        return null;
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private void checkReflection(Workout workout) {
        var trainReflection = workout.getReflection();
        if (trainReflection.isEmpty()) {
            workout.setReflection(null);
        }
    }

}
