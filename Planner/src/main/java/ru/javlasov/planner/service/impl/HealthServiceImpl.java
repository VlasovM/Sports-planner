package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.HealthMapper;
import ru.javlasov.planner.model.Health;
import ru.javlasov.planner.repository.HealthRepository;
import ru.javlasov.planner.service.HealthService;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthMapper healthMapper;

    private final HealthRepository healthRepository;

    private final UserCredentialsService userCredentialsService;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public List<HealthDto> getHealthCurrentUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser().getUser();
        List<Health> findHealth = healthRepository.findAllByUser(currentUser);
        return healthMapper.modelListToDtoList(findHealth);
    }

    @Override
    @Transactional(readOnly = true)
    public HealthDto getById(Long id) {
        var health = findById(id);
        return healthMapper.modelToDto(health);
    }

    private void sendMessage(String message) {
        var loggingDto = new LoggerEvent(message, TypeMessage.ERROR);
        loggingService.sendMessage(loggingDto);
    }

    private Health findById(Long healthId) {
        return healthRepository.findById(healthId)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке отчет по проверке здоровья по id %d"
                            .formatted(healthId));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

}
