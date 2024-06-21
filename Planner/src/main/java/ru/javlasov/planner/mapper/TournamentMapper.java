package ru.javlasov.planner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.javlasov.planner.dto.TournamentDto;
import ru.javlasov.planner.model.Tournament;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TournamentMapper {

    @Mapping(source = "user", target = "userDto")
    TournamentDto modelToDto(Tournament tournament);

    @Mapping(source = "userDto", target = "user")
    Tournament dtoToModel(TournamentDto dto);

    List<TournamentDto> listModelToListDto(List<Tournament> modelList);

}
