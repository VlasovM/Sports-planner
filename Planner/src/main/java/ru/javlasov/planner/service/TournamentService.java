package ru.javlasov.planner.service;

import ru.javlasov.planner.dto.TournamentDto;

import java.util.List;

public interface TournamentService {

    void deleteById(Long id);

    void updateOrCreate(TournamentDto tournamentDto);

    List<TournamentDto> getTournamentCurrentUser();

    TournamentDto getById(Long id);

}
