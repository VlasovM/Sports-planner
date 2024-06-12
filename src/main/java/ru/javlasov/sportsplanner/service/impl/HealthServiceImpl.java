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
        findById(id);
        healthRepository.deleteById(id);
        sendMessage("Пользователь %s удалил проверку здоровья с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void updateOrCreate(HealthDto healthDto) {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var currentUser = currentUserCredentials.getUser();
        var health = healthMapper.dtoToModel(healthDto);
        health.setUser(currentUser);
        health = healthRepository.save(health);
        sendMessage("Пользователь %s %s проверку здоровья с id = %d".formatted(
                currentUserCredentials.getEmail(), healthDto.getId() == null ? "создал" : "изменил",
                health.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthDto> getHealthCurrentUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser().getUser();
        List<Health> findHealth = healthRepository.findAllByUser(currentUser);
        return healthMapper.modelListToDtoList(findHealth);
    }

    @Override
    public HealthDto getById(Long id) {
        var health = findById(id);
        return healthMapper.modelToDto(health);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private Health findById(Long healthId) {
        return healthRepository.findById(healthId)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти посещение врача по id %d".formatted(healthId), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

}
