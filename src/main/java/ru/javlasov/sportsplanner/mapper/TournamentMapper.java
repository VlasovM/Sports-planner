package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TournamentMapper {

    TournamentDto modelToDto(Tournament tournament);

    Tournament dtoToModel(TournamentDto dto);

    List<TournamentDto> listModelToListDto(List<Tournament> modelList);

}
