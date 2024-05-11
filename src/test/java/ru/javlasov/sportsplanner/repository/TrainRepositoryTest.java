package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Train;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @DisplayName("Should get all trains")
    @Test
    void getAllTrainsTest() {
        //given
        List<Train> expectedTrains = ExpectedDataFromDB.getExpectedTrainsFromDB();

        //when
        List<Train> actualTrains = trainRepository.findAll();

        //then
        assertThat(expectedTrains.size()).isEqualTo(actualTrains.size());
        assertThat(expectedTrains.get(0).getId()).isEqualTo(actualTrains.get(0).getId());
        assertThat(expectedTrains.get(0).getDescription()).isEqualTo(expectedTrains.get(0).getDescription());
        assertThat(expectedTrains.get(1).getId()).isEqualTo(actualTrains.get(1).getId());
        assertThat(expectedTrains.get(1).getDescription()).isEqualTo(expectedTrains.get(1).getDescription());
    }

    @DisplayName("Should get train by id")
    @Test
    void getTrainByIdTest() {
        //given
        var expectedTrain = ExpectedDataFromDB.getExpectedTrainsFromDB().get(0);

        //when
        var actualTrain = trainRepository.findById(1L);

        //then
        assertThat(actualTrain).isPresent();
        assertThat(actualTrain.get().getId()).isEqualTo(expectedTrain.getId());
        assertThat(actualTrain.get().getUser()).isEqualTo(expectedTrain.getUser());
    }


}