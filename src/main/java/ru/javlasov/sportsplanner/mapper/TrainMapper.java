package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.model.Train;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TrainMapper {

    default TrainDto modelToDto(Train model) {
        if (model == null) {
            return null;
        }

        TrainDto trainDto = new TrainDto();

        trainDto.setId(model.getId());
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ENGLISH);
        var correctDate = model.getDate().format(dateTimeFormatter);
        trainDto.setDate(correctDate);
        trainDto.setTitle(model.getTitle());
        trainDto.setReflection(model.getReflection() == null ? "-" : model.getReflection());
        trainDto.setUser(model.getUser());

        return trainDto;
    }

    List<TrainDto> modelListToDtoList(List<Train> modelList);

    Train dtoToModel(TrainDto dto);

    List<TrainDto> modelSetToDtoList(Set<Train> models);

}
