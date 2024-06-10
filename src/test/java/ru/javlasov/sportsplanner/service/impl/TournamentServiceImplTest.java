//package ru.javlasov.sportsplanner.service.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.javlasov.sportsplanner.expection.NotFoundException;
//import ru.javlasov.sportsplanner.mapper.TournamentMapper;
//import ru.javlasov.sportsplanner.repository.TournamentRepository;
//import ru.javlasov.sportsplanner.service.LoggingService;
//import ru.javlasov.sportsplanner.service.TournamentService;
//import ru.javlasov.sportsplanner.service.UserCredentialsService;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class TournamentServiceImplTest {
//
//    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);
//
//    private final TournamentMapper mockTournamentMapper = Mockito.mock(TournamentMapper.class);
//
//    private final TournamentRepository mockTournamentRepository = Mockito.mock(TournamentRepository.class);
//
//    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);
//
//    private final TournamentService underTestService = new TournamentServiceImpl(mockTournamentMapper,
//            mockTournamentRepository, mockUserCredentialsService, mockLoggingService);
//
//    @Test
//    @DisplayName("Should get exception when cannot find by user id")
//    void deleteByIdTest() {
//        // given
//        Mockito.when(mockTournamentRepository.findById(1L)).thenReturn(Optional.empty());
//
//        // when
//        NotFoundException actualException = assertThrows(NotFoundException.class,
//                () -> underTestService.deleteById(1L));
//
//        // then
//        assertThat(actualException).isNotNull();
//        assertThat(actualException.getClass()).isEqualTo(NotFoundException.class);
//        assertThat(actualException.getMessage()).isEqualTo("Возникла ошибка с получением данных, " +
//                "обратитесь к администратору системы.");
//    }
//
//}