//package ru.javlasov.sportsplanner.service.impl;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.javlasov.sportsplanner.expection.NotFoundException;
//import ru.javlasov.sportsplanner.mapper.HealthMapper;
//import ru.javlasov.sportsplanner.repository.HealthRepository;
//import ru.javlasov.sportsplanner.service.HealthService;
//import ru.javlasov.sportsplanner.service.LoggingService;
//import ru.javlasov.sportsplanner.service.UserCredentialsService;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//class HealthServiceImplTest {
//
//    private final LoggingService mockLoggingService =
//            Mockito.mock(LoggingService.class);
//
//    private final HealthMapper mockHealthMapper = Mockito.mock(HealthMapper.class);
//
//    private final HealthRepository mockHealthRepository = Mockito.mock(HealthRepository.class);
//
//    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);
//
//    private final HealthService underTestService = new HealthServiceImpl(mockHealthMapper, mockHealthRepository,
//            mockUserCredentialsService, mockLoggingService);
//
//    @Test
//    @DisplayName("Should get exception when don't find health by user id")
//    void deleteByIdNotExistsHealthTest() {
//        // given
//        Mockito.when(mockHealthRepository.findById(1L)).thenReturn(Optional.empty());
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