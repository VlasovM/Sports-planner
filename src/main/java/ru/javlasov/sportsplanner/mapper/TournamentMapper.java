//package ru.javlasov.sportsplanner.mapper;
//
//import org.mapstruct.Mapper;
//import ru.javlasov.sportsplanner.dto.TournamentDto;
//import ru.javlasov.sportsplanner.model.Tournament;
//
//import java.util.List;
//import java.util.Set;
//
//@Mapper(componentModel = "spring")
//public interface TournamentMapper {
//
//    Tournament dtoToModel(TournamentDto dto);
//
//    default TournamentDto modelToDto(Tournament model) {
//        if (model == null) {
//            return null;
//        }
//
//        //TODO
//        TournamentDto tournamentDto = new TournamentDto();
////
////        tournamentDto.setId(model.getId());
////        tournamentDto.setDate(model.getDate());
////        tournamentDto.setTitle(model.getTitle());
////        tournamentDto.setOpponent(model.getOpponent());
////        tournamentDto.setResult(model.getResult());
////        tournamentDto.setReflection(model.getReflection() == null ? "-" : model.getReflection());
////        tournamentDto.setUser(model.getUser());
//
//        return tournamentDto;
//    }
//
//    List<TournamentDto> listModelToListDto(List<Tournament> modelList);
//
//    List<TournamentDto> setModelToDtoList(Set<Tournament> modelSet);
//
//}
