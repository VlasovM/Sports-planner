package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TrainMapper;
import ru.javlasov.sportsplanner.repository.TrainRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TrainService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    private final UserCredentialsService userCredentialsService;

    private final TrainMapper trainMapper;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public List<TrainDto> getAllTrainsCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var trainsUser = trainRepository.findAllByUser(currentUserCredentials.getUser().getId());
        return trainMapper.modelListToDtoList(trainsUser);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        trainRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти тренировку по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        trainRepository.deleteById(id);
        sendMessage("Пользователь %s удалил тренировку с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void createOrEdit(TrainDto trainDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        trainDto.setUser(currentUser.getId());
        var trainAfterSave = trainRepository.save(trainMapper.dtoToModel(trainDto));
        sendMessage("Пользователь %s %s проверку здоровья с id = %d".formatted(
                currentUser.getEmail(), trainDto.getId() == null ? "создал" : "изменил",
                trainAfterSave.getId()), TypeMessage.INFO);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

}
