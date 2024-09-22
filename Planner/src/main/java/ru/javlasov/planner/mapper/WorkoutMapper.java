package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.WorkoutDto;
import ru.javlasov.planner.model.Workout;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface WorkoutMapper {

    @Mapping(source = "user", target = "userDto")
    WorkoutDto modelToDto(Workout model);

    List<WorkoutDto> modelListToDtoList(List<Workout> modelList);

    @Mapping(source = "userDto", target = "user")
    Workout dtoToModel(WorkoutDto dto);

}
