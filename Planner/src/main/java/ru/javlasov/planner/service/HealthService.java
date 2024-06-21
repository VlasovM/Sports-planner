package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.HealthDto;

import java.util.List;

public interface HealthService {

    void deleteById(Long id);

    void updateOrCreate(HealthDto healthDto);

    List<HealthDto> getHealthCurrentUser();

    HealthDto getById(Long id);

}
