package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.model.Health;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface HealthMapper {

    @Mapping(source = "user", target = "userDto")
    HealthDto modelToDto(Health model);

    List<HealthDto> modelListToDtoList(List<Health> modelList);

    @Mapping(source = "userDto", target = "user")
    Health dtoToModel(HealthDto dto);

}
