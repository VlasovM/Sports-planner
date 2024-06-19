package ru.javlasov.sportsplanner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
public class HealthMapperTest {

    @Autowired
    private HealthMapper healthMapper;

    @Test
    @DisplayName("Should convert model to dto")
    void modelToDtoTest() {
        // given
        var incomeHealth = ExpectedDataFromDB.getExpectedHealthFromDB().get(0);
        var expectedHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB().get(0);
        expectedHealthDto.getUserDto().setEmail(null);
        expectedHealthDto.getUserDto().setPassword(null);

        // when
        var actualHealthDto = healthMapper.modelToDto(incomeHealth);

        // then
        assertThat(actualHealthDto).usingRecursiveComparison().isEqualTo(expectedHealthDto);
    }

    @Test
    @DisplayName("Should convert dto to model")
    void dtoToModelTest() {
        // given
        var incomeHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB().get(0);
        var expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB().get(0);

        // when
        var actualHealth = healthMapper.dtoToModel(incomeHealthDto);

        // then
        assertThat(actualHealth).usingRecursiveComparison().isEqualTo(expectedHealth);
    }

}
