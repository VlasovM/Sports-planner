package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TournamentMapper;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.repository.TournamentRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TournamentService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

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
        tournamentRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти турнир по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        tournamentRepository.deleteById(id);
        sendMessage("Пользователь %s удалил инфо о турнире с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(), id), TypeMessage.INFO);
    }

    @Override
    @Transactional
    public void createOrEdit(TournamentDto tournamentDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        tournamentDto.setUser(currentUser.getUser().getId());
        var tournamentEntity = tournamentMapper.dtoToModel(tournamentDto);
        checkReflection(tournamentEntity);
        var tournamentAfterSave = tournamentRepository.save(tournamentEntity);
        sendMessage("Пользователь %s %s новую инфо о турнире с id = %d".formatted(
                userCredentialsService.getCurrentAuthUser().getEmail(),
                tournamentDto.getId() == null ? "создал" : "изменил", tournamentAfterSave.getId()), TypeMessage.INFO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentDto> getTournamentCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        List<Tournament> trains = tournamentRepository.findAllByUser(currentUserCredentials.getUser().getId());
        return tournamentMapper.listModelToListDto(trains);
    }

    @Override
    @Transactional(readOnly = true)
    public TournamentDto getById(Long id) {
        var tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти турнир по id %d".formatted(id), TypeMessage.ERROR);
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        return tournamentMapper.modelToDto(tournament);
    }

    private void sendMessage(String message, TypeMessage type) {
        var loggingDto = new LoggerEvent(message, type);
        loggingService.sendMessage(loggingDto);
    }

    private void checkReflection(Tournament tournament) {
        var tournamentReflection = tournament.getReflection();
        if (tournamentReflection.isEmpty()) {
            tournament.setReflection(null);
        }
    }

}
