package ru.javlasov.sportsplanner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
public class WorkoutMapperTest {

    @Autowired
    private WorkoutMapper workoutMapper;

    @Test
    @DisplayName("Should convert model to dto")
    void modelToDtoTest() {
        // given
        var incomeWorkout = ExpectedDataFromDB.getExpectedWorkoutFromDB().get(0);
        var expectedWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);
        expectedWorkoutDto.getUserDto().setPassword(null);
        expectedWorkoutDto.getUserDto().setEmail(null);

        // when
        var actualWorkoutDto = workoutMapper.modelToDto(incomeWorkout);

        // then
        assertThat(actualWorkoutDto).usingRecursiveComparison().isEqualTo(expectedWorkoutDto);
    }

    @Test
    @DisplayName("Should convert dto to model")
    void dtoToModelTest() {
        // given
        var incomeWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);
        var expectedWorkout = ExpectedDataFromDB.getExpectedWorkoutFromDB().get(0);

        // when
        var actualWorkout = workoutMapper.dtoToModel(incomeWorkoutDto);

        // then
        assertThat(actualWorkout).usingRecursiveComparison().isEqualTo(expectedWorkout);
    }

}
