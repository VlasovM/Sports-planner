package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.planner.dto.SportDto;
import ru.javlasov.planner.model.Sport;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportMapper {

    SportDto modelToDto(Sport model);

    List<SportDto> listModelToListDto(List<Sport> modelList);

}
