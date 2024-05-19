package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TrainMapper;
import ru.javlasov.sportsplanner.repository.TrainRepository;
import ru.javlasov.sportsplanner.service.TrainService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    private final UserCredentialsService userCredentialsService;

    private final TrainMapper trainMapper;

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
                    log.error("Ошибка при попытке найти тренировку по id %d".formatted(id));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        trainRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createOrEdit(TrainDto trainDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        trainDto.setUser(currentUser.getId());
        trainRepository.save(trainMapper.dtoToModel(trainDto));
    }

}
