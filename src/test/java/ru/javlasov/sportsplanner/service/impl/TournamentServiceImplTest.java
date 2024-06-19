package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.dto.TournamentDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TournamentMapper;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.TournamentRepository;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TournamentService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class TournamentServiceImplTest {

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final TournamentMapper mockTournamentMapper = Mockito.mock(TournamentMapper.class);

    private final TournamentRepository mockTournamentRepository = Mockito.mock(TournamentRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final UserCredentialsRepository mockUserCredentialsRepository
            = Mockito.mock(UserCredentialsRepository.class);

    private final TournamentService underTestService = new TournamentServiceImpl(mockTournamentMapper,
            mockTournamentRepository, mockUserCredentialsService, mockLoggingService);

    @Test
    @DisplayName("Should get all tournaments for current user")
    void getAllTournamentTest() {
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUser = expectedUserCredentials.getUser();
        List<TournamentDto> expectedTournamentsDto = ExpectedDataFromDB.getExpectedTournamentsDtoFromDB();
        List<Tournament> expectedTournaments = ExpectedDataFromDB.getExpectedTournamentsFromDB();

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockTournamentRepository.findAllByUser(expectedUser)).thenReturn(expectedTournaments);
        Mockito.when(mockTournamentMapper.listModelToListDto(expectedTournaments)).thenReturn(expectedTournamentsDto);

        var actualTournamentsDto = underTestService.getTournamentCurrentUser();

        // then
        assertThat(actualTournamentsDto.size()).isEqualTo(expectedTournamentsDto.size());
        assertThat(actualTournamentsDto).usingRecursiveComparison().isEqualTo(expectedTournamentsDto);
    }

    @Test
    @DisplayName("Should get exception when cannot find by user id")
    void deleteByIdTest() {
        // given
        Mockito.when(mockTournamentRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        NotFoundException actualException = assertThrows(NotFoundException.class,
                () -> underTestService.deleteById(1L));

        // then
        assertThat(actualException).isNotNull();
        assertThat(actualException.getClass()).isEqualTo(NotFoundException.class);
        assertThat(actualException.getMessage()).isEqualTo("Возникла ошибка с получением данных, " +
                "обратитесь к администратору системы.");
    }

    @Test
    @DisplayName("Should get tournament by id")
    void getByIdTest() {
        // given
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);
        var expectedTournamentDto = ExpectedDataFromDB.getExpectedTournamentsDtoFromDB().get(0);
        var incomeId = expectedTournament.getId();

        // when
        Mockito.when(mockTournamentRepository.findById(incomeId)).thenReturn(Optional.of(expectedTournament));
        Mockito.when(mockTournamentMapper.modelToDto(expectedTournament)).thenReturn(expectedTournamentDto);
        var actualHealthDto = underTestService.getById(incomeId);

        // then
        assertThat(actualHealthDto).usingRecursiveComparison().isEqualTo(expectedTournamentDto);
    }

    @Test
    @DisplayName("Should create tournament")
    void createHealthTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var incomeTournamentDto = ExpectedDataFromDB.getExpectedTournamentsDtoFromDB().get(0);
        incomeTournamentDto.setId(null);
        var incomeTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);
        incomeTournament.setId(null);
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);

        // when
        makeMockAuthUser(expectedUserCredentials);
        Mockito.when(mockTournamentRepository.save(any())).thenReturn(expectedTournament);
        Mockito.when(mockTournamentMapper.dtoToModel(incomeTournamentDto)).thenReturn(incomeTournament);
        underTestService.updateOrCreate(incomeTournamentDto);

        // then
    }

    private void makeMockAuthUser(UserCredentials expectedUserCredentials) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
    }

}