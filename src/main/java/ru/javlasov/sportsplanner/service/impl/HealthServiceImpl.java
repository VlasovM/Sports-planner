package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.HealthMapper;
import ru.javlasov.sportsplanner.repository.HealthRepository;
import ru.javlasov.sportsplanner.service.HealthService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class HealthServiceImpl implements HealthService {

    private final HealthMapper healthMapper;

    private final HealthRepository healthRepository;

    private final UserCredentialsService userCredentialsService;


    @Override
    @Transactional
    public void deleteById(Long id) {
        healthRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка при попытке найти посещение врача по id %d".formatted(id));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        healthRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createOrEdit(HealthDto healthDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        healthDto.setUser(currentUser.getId());
        healthRepository.save(healthMapper.dtoToModel(healthDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthDto> getHealthCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var trainsUser = healthRepository.findAllByUser(currentUserCredentials.getUser().getId());
        return healthMapper.modelListToDtoList(trainsUser);
    }

}
