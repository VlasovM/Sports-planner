package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.dto.TournamentDto;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.TournamentMapper;
import ru.javlasov.planner.model.Tournament;
import ru.javlasov.planner.repository.TournamentRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.TournamentService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentMapper tournamentMapper;

    private final TournamentRepository tournamentRepository;

    private final UserCredentialsService userCredentialsService;

    private final LoggingService loggingService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        tournamentRepository.deleteById(id);
        sendMessage("Пользователь %s удалил инфо о турнире с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void updateOrCreate(TournamentDto tournamentDto) {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        var currentUser = currentUserCredentials.getUser();
        var tournament = tournamentMapper.dtoToModel(tournamentDto);
        tournament.setUser(currentUser);
        tournament = tournamentRepository.save(tournament);
        sendMessage("Пользователь %s %s новую инфо о турнире с id = %d".formatted(
                currentUserCredentials.getEmail(),
                tournamentDto.getId() == null ? "создал" : "изменил", tournament.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentDto> getTournamentCurrentUser() {
        var currentUser = userCredentialsService.getCurrentAuthUser().getUser();
        var findTournaments = tournamentRepository.findAllByUser(currentUser);
        return tournamentMapper.listModelToListDto(findTournaments);
    }

    @Override
    @Transactional(readOnly = true)
    public TournamentDto getById(Long id) {
        var tournament = findById(id);
        return tournamentMapper.modelToDto(tournament);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private Tournament findById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти турнир по id %d".formatted(tournamentId), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

}
