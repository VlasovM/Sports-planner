package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TournamentMapper;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.repository.TournamentRepository;
import ru.javlasov.sportsplanner.service.TournamentService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentMapper tournamentMapper;

    private final TournamentRepository tournamentRepository;

    private final UserCredentialsService userCredentialsService;

    @Override
    @Transactional
    public void deleteById(Long id) {
        tournamentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Ошибка при попытке найти турнир по id %d".formatted(id));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        tournamentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createOrEdit(TournamentDto tournamentDto) {
        var currentUser = userCredentialsService.getCurrentAuthUser();
        tournamentDto.setUser(currentUser.getId());
        tournamentRepository.save(tournamentMapper.dtoToModel(tournamentDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TournamentDto> getTournamentCurrentUser() {
        var currentUserCredentials = userCredentialsService.getCurrentAuthUser();
        List<Tournament> trains = tournamentRepository.findAllByUser(currentUserCredentials.getUser().getId());
        return tournamentMapper.listModelToListDto(trains);
    }

}
