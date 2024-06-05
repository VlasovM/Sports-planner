package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.model.Health;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface HealthMapper {

    HealthDto modelToDto(Health model);

    Health dtoToModel(HealthDto dto);

    List<HealthDto> modelListToDtoList(List<Health> modelList);

    List<HealthDto> modelSetToDtoList(Set<Health> modelSet);

}
