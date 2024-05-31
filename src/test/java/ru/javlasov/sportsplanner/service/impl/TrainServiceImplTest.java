package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TrainMapper;
import ru.javlasov.sportsplanner.repository.TrainRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TrainService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class TrainServiceImplTest {

    private final TrainRepository mockTrainRepository = Mockito.mock(TrainRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final TrainMapper mockTrainMapper = Mockito.mock(TrainMapper.class);

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final TrainService underTestService = new TrainServiceImpl(mockTrainRepository, mockUserCredentialsService,
            mockTrainMapper, mockLoggingService);

    @Test
    @DisplayName("Should get all trains for current user")
    void getAllTrainsCurrentUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);

        var expectedTrains = ExpectedDataFromDB.getExpectedTrainsFromDB();
        Mockito.when(mockTrainRepository.findAllByUser(expectedUserCredentials.getUser().getId())).thenReturn(expectedTrains);

        var expectedTrainsDto = ExpectedDataFromDB.getExpectedTrainDtoFromDB();
        Mockito.when(mockTrainMapper.modelListToDtoList(expectedTrains))
                .thenReturn(ExpectedDataFromDB.getExpectedTrainDtoFromDB());

        // when
        var actualTrainsDto = underTestService.getAllTrainsCurrentUser();

        // then
        assertThat(expectedTrainsDto.size()).isEqualTo(actualTrainsDto.size());
        assertThat(expectedTrainsDto.get(0).getId()).isEqualTo(actualTrainsDto.get(0).getId());
        assertThat(expectedTrainsDto.get(0).getTitle()).isEqualTo(actualTrainsDto.get(0).getTitle());
        assertThat(expectedTrainsDto.get(0).getUser()).isEqualTo(actualTrainsDto.get(0).getUser());
        assertThat(expectedTrainsDto.get(1).getId()).isEqualTo(actualTrainsDto.get(1).getId());
        assertThat(expectedTrainsDto.get(1).getTitle()).isEqualTo(actualTrainsDto.get(1).getTitle());
        assertThat(expectedTrainsDto.get(1).getUser()).isEqualTo(actualTrainsDto.get(1).getUser());
    }

    @Test
    @DisplayName("Should delete train by id if not exists train by id")
    void deleteByIdNotExistsTrainTest() {
        // given
        Mockito.when(mockTrainRepository.findById(1L)).thenReturn(Optional.empty());

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