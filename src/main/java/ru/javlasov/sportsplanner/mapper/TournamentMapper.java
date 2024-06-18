package ru.javlasov.sportsplanner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TournamentMapper {

    @Mapping(source = "user", target = "userDto")
    TournamentDto modelToDto(Tournament tournament);

    @Mapping(source = "userDto", target = "user")
    Tournament dtoToModel(TournamentDto dto);

    List<TournamentDto> listModelToListDto(List<Tournament> modelList);

}
