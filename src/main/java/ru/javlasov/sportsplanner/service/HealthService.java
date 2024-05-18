package ru.javlasov.sportsplanner.service;

import ru.javlasov.sportsplanner.dto.HealthDto;

import java.util.List;

public interface HealthService {

    void deleteById(Long id);

    void saveOrCreate(HealthDto healthDto);

    List<HealthDto> getHealthCurrentUser();

}
