package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.HealthMapper;
import ru.javlasov.sportsplanner.repository.HealthRepository;
import ru.javlasov.sportsplanner.service.HealthService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class HealthServiceImplTest {

    private final LoggingService mockLoggingService =
            Mockito.mock(LoggingService.class);

    private final HealthMapper mockHealthMapper = Mockito.mock(HealthMapper.class);

    private final HealthRepository mockHealthRepository = Mockito.mock(HealthRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final HealthService underTestService = new HealthServiceImpl(mockHealthMapper, mockHealthRepository,
            mockUserCredentialsService, mockLoggingService);

    @Test
    @DisplayName("Should get health for current user")
    void getHealthCurrentUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);

        var expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB();
        Mockito.when(mockHealthRepository.findAllByUser(expectedUserCredentials.getUser().getId())).thenReturn(expectedHealth);

        var expectedHealthDto = ExpectedDataFromDB.getExpectedHealthDto();
        Mockito.when(mockHealthMapper.modelListToDtoList(expectedHealth)).thenReturn(expectedHealthDto);

        // when
        var actualHealthDto = underTestService.getHealthCurrentUser();

        // then
        assertThat(expectedHealthDto.size()).isEqualTo(actualHealthDto.size());
        assertThat(expectedHealthDto.get(0).getUser()).isEqualTo(actualHealthDto.get(0).getUser());
        assertThat(expectedHealthDto.get(0).getId()).isEqualTo(actualHealthDto.get(0).getId());
        assertThat(expectedHealthDto.get(0).getClinic()).isEqualTo(actualHealthDto.get(0).getClinic());
        assertThat(expectedHealthDto.get(0).getDoctorFullName()).isEqualTo(actualHealthDto.get(0).getDoctorFullName());
        assertThat(expectedHealthDto.get(1).getUser()).isEqualTo(actualHealthDto.get(1).getUser());
        assertThat(expectedHealthDto.get(1).getId()).isEqualTo(actualHealthDto.get(1).getId());
        assertThat(expectedHealthDto.get(1).getClinic()).isEqualTo(actualHealthDto.get(1).getClinic());
        assertThat(expectedHealthDto.get(1).getDoctorFullName()).isEqualTo(actualHealthDto.get(1).getDoctorFullName());

    }

    @Test
    @DisplayName("Should get exception when don't find health by user id")
    void deleteByIdNotExistsHealthTest() {
        // given
        Mockito.when(mockHealthRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        NotFoundException actualException = assertThrows(NotFoundException.class,
                () -> underTestService.deleteById(1L));

        // then
        assertThat(actualException).isNotNull();
        assertThat(actualException.getClass()).isEqualTo(NotFoundException.class);
        assertThat(actualException.getMessage()).isEqualTo("Возникла ошибка с получением данных, " +
                "обратитесь к администратору системы.");
    }

}