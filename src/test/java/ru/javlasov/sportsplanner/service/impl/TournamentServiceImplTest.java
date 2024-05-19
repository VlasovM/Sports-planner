package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TournamentMapper;
import ru.javlasov.sportsplanner.repository.TournamentRepository;
import ru.javlasov.sportsplanner.service.TournamentService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TournamentServiceImplTest {

    private final TournamentMapper mockTournamentMapper = Mockito.mock(TournamentMapper.class);

    private final TournamentRepository mockTournamentRepository = Mockito.mock(TournamentRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final TournamentService underTestService = new TournamentServiceImpl(mockTournamentMapper,
            mockTournamentRepository, mockUserCredentialsService);

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
    @DisplayName("Should get all tournaments for current auth user")
    void getTournamentCurrentUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);

        var expectedTournaments = ExpectedDataFromDB.getExpectedTournamentsFromDB();
        Mockito.when(mockTournamentRepository.findAllByUser(expectedUserCredentials.getUser().getId()))
                .thenReturn(expectedTournaments);

        var expectedTournamentDto = ExpectedDataFromDB.getExpectedTournamentDto();
        Mockito.when(mockTournamentMapper.listModelToListDto(expectedTournaments)).thenReturn(expectedTournamentDto);

        // when
        var actualTournamentDto = underTestService.getTournamentCurrentUser();

        // then
        assertThat(expectedTournamentDto.size()).isEqualTo(actualTournamentDto.size());
        assertThat(expectedTournamentDto.get(0).getId()).isEqualTo(actualTournamentDto.get(0).getId());
        assertThat(expectedTournamentDto.get(0).getUser()).isEqualTo(actualTournamentDto.get(0).getUser());
        assertThat(expectedTournamentDto.get(0).getOpponent()).isEqualTo(actualTournamentDto.get(0).getOpponent());
        assertThat(expectedTournamentDto.get(0).getTitle()).isEqualTo(actualTournamentDto.get(0).getTitle());
        assertThat(expectedTournamentDto.get(1).getId()).isEqualTo(actualTournamentDto.get(1).getId());
        assertThat(expectedTournamentDto.get(1).getUser()).isEqualTo(actualTournamentDto.get(1).getUser());
        assertThat(expectedTournamentDto.get(1).getOpponent()).isEqualTo(actualTournamentDto.get(1).getOpponent());
        assertThat(expectedTournamentDto.get(1).getTitle()).isEqualTo(actualTournamentDto.get(1).getTitle());
    }

}