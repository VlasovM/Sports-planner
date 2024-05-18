package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.model.Train;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainMapper {

    TrainDto modelToDto(Train model);

    List<TrainDto> modelListToDtoList(List<Train> modelList);

    Train dtoToModel(TrainDto dto);

}
