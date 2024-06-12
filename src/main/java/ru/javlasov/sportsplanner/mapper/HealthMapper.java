package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.model.Health;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HealthMapper {

    HealthDto modelToDto(Health model);

    List<HealthDto> modelListToDtoList(List<Health> modelList);

    Health dtoToModel(HealthDto dto);

}
