package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Workout;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Test
    @DisplayName("Should get all workout by user id")
    void findAllByUserTest() {
        // given
        List<Workout> expectedWorkout = ExpectedDataFromDB.getExpectedWorkoutFromDB();
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        // when

        var actualWorkout = workoutRepository.findAllByUser(expectedUser);

        // then
        assertThat(actualWorkout).isNotEmpty();
        assertThat(expectedWorkout).usingRecursiveComparison().isEqualTo(actualWorkout);
    }

}
