package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.TrainDto;

import java.util.List;

public interface TrainService {

    List<TrainDto> getAllTrainsCurrentUser();

    void deleteById(Long id);

    void createOrEdit(TrainDto trainDto);

    TrainDto getById(Long id);

}
