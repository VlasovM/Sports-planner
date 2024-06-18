package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.model.Health;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface HealthMapper {

    @Mapping(source = "user", target = "userDto")
    HealthDto modelToDto(Health model);

    List<HealthDto> modelListToDtoList(List<Health> modelList);

    @Mapping(source = "userDto", target = "user")
    Health dtoToModel(HealthDto dto);

}
