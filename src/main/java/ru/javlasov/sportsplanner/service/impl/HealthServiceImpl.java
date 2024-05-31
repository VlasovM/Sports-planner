package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.HealthMapper;
import ru.javlasov.sportsplanner.model.Health;
import ru.javlasov.sportsplanner.repository.HealthRepository;
import ru.javlasov.sportsplanner.service.HealthService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthMapper healthMapper;

    private final HealthRepository healthRepository;

    private final UserCredentialsService userCredentialsService;

    private final LoggingService loggingService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        healthRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти посещение врача по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        healthRepository.deleteById(id);
        sendMessage("Пользователь %s удалил проверку здоровья с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void createOrEdit(HealthDto healthDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        healthDto.setUser(currentUser.getId());
        Health healthAfterSave = healthRepository.save(healthMapper.dtoToModel(healthDto));
        sendMessage("Пользователь %s %s проверку здоровья с id = %d".formatted(
                currentUser.getEmail(), healthDto.getId() == null ? "создал" : "изменил",
                healthAfterSave.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthDto> getHealthCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var trainsUser = healthRepository.findAllByUser(currentUserCredentials.getUser().getId());
        return healthMapper.modelListToDtoList(trainsUser);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

}
