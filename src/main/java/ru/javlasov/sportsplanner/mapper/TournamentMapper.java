package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    Tournament dtoToModel(TournamentDto dto);

    TournamentDto modelToDto(Tournament model);

    List<TournamentDto> listModelToListDto(List<Tournament> modelList);

}
