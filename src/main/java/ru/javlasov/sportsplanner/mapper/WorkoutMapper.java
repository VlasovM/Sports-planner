package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.WorkoutDto;
import ru.javlasov.sportsplanner.model.Workout;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkoutMapper {

    WorkoutDto modelToDto(Workout model);

    List<WorkoutDto> modelListToDtoList(List<Workout> modelList);

    Workout dtoToModel(WorkoutDto dto);

}
