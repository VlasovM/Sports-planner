package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Train;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @DisplayName("Should get all trains")
    @Test
    void getAllTrainsTest() {
        // given
        Set<Train> expectedTrainsSet = ExpectedDataFromDB.getExpectedTrainsFromDB();
        List<Train> expectedTrainsList = new ArrayList<>(expectedTrainsSet);
        expectedTrainsList = expectedTrainsList
                .stream()
                .sorted(Comparator.comparing(Train::getId)).
                collect(Collectors.toList());

        // when
        List<Train> actualTrains = trainRepository.findAll();

        // then
        assertThat(expectedTrainsList.size()).isEqualTo(actualTrains.size());
        assertThat(expectedTrainsList.get(0).getId()).isEqualTo(actualTrains.get(0).getId());
        assertThat(expectedTrainsList.get(0).getTitle()).isEqualTo(actualTrains.get(0).getTitle());
        assertThat(expectedTrainsList.get(1).getId()).isEqualTo(actualTrains.get(1).getId());
        assertThat(expectedTrainsList.get(1).getTitle()).isEqualTo(actualTrains.get(1).getTitle());
    }

    @DisplayName("Should get train by id")
    @Test
    void getTrainByIdTest() {
        // given
        var expectedTrain = ExpectedDataFromDB.getExpectedTrainsFromDB().stream()
                .filter(train -> train.getId().equals(1L))
                .findFirst().orElseThrow();

        // when
        var actualTrain = trainRepository.findById(1L);

        // then
        assertThat(actualTrain).isPresent();
        assertThat(actualTrain.get().getId()).isEqualTo(expectedTrain.getId());
        assertThat(actualTrain.get().getUser()).isEqualTo(expectedTrain.getUser());
    }

    @Test
    @DisplayName("Should get all trains by user id")
    void findAllByUserTest() {
        // given
        Set<Train> expectedTrainsSet = ExpectedDataFromDB.getExpectedTrainsFromDB();
        List<Train> expectedTrainsList = new ArrayList<>(expectedTrainsSet);
        expectedTrainsList = expectedTrainsList
                .stream()
                .sorted(Comparator.comparing(Train::getId)).
                collect(Collectors.toList());

        // when

        var actualTrainsDto = trainRepository.findAllByUser(1L);

        // then
        assertThat(actualTrainsDto).isNotEmpty();
        assertThat(expectedTrainsList.get(0).getId()).isEqualTo(actualTrainsDto.get(0).getId());
        assertThat(expectedTrainsList.get(0).getTitle()).isEqualTo(actualTrainsDto.get(0).getTitle());
    }

}