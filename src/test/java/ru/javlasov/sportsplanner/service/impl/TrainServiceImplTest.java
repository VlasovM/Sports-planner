package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.dto.TrainDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.TrainMapper;
import ru.javlasov.sportsplanner.model.Train;
import ru.javlasov.sportsplanner.repository.TrainRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.TrainService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        Set<Train> expectedTrainsSet = ExpectedDataFromDB.getExpectedTrainsFromDB();
        List<Train> expectedTrainsList = new ArrayList<>(expectedTrainsSet);
        Mockito.when(mockTrainRepository.findAllByUser(expectedUserCredentials.getUser().getId()))
                .thenReturn(expectedTrainsList);

        Set<TrainDto> expectedTrainsDtoSet = ExpectedDataFromDB.getExpectedTrainDtoFromDB();
        List<TrainDto> expectedTrainsDtoList = new ArrayList<>(expectedTrainsDtoSet);
        Mockito.when(mockTrainMapper.modelListToDtoList(expectedTrainsList))
                .thenReturn(expectedTrainsDtoList);

        // when
        var actualTrainsDto = underTestService.getAllTrainsCurrentUser();

        // then
        assertThat(expectedTrainsDtoList.size()).isEqualTo(actualTrainsDto.size());
        assertThat(expectedTrainsDtoList.get(0).getId()).isEqualTo(actualTrainsDto.get(0).getId());
        assertThat(expectedTrainsDtoList.get(0).getTitle()).isEqualTo(actualTrainsDto.get(0).getTitle());
        assertThat(expectedTrainsDtoList.get(0).getUser()).isEqualTo(actualTrainsDto.get(0).getUser());
        assertThat(expectedTrainsDtoList.get(1).getId()).isEqualTo(actualTrainsDto.get(1).getId());
        assertThat(expectedTrainsDtoList.get(1).getTitle()).isEqualTo(actualTrainsDto.get(1).getTitle());
        assertThat(expectedTrainsDtoList.get(1).getUser()).isEqualTo(actualTrainsDto.get(1).getUser());
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