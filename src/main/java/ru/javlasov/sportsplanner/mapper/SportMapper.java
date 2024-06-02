package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.SportDto;
import ru.javlasov.sportsplanner.model.Sport;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SportMapper {

    SportDto modelToDto(Sport model);

    List<SportDto> listModelToListDto(List<Sport> modelList);

}
