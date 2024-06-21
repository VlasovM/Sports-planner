package ru.javlasov.planner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.planner.ExpectedDataFromDB;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
public class TournamentMapperTest {

    @Autowired
    private TournamentMapper tournamentMapper;

    @Test
    @DisplayName("Should convert dto to model")
    void dtoToModelTest() {
        // given
        var incomeTournamentDto = ExpectedDataFromDB.getExpectedTournamentsDtoFromDB().get(0);
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);

        // when
        var actualTournament = tournamentMapper.dtoToModel(incomeTournamentDto);

        // then
        assertThat(actualTournament).usingRecursiveComparison().isEqualTo(expectedTournament);
    }

    @Test
    @DisplayName("Should convert model to dto")
    void modelToDtoTest() {
        // given
        var incomeTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);
        var expectedTournamentDto = ExpectedDataFromDB.getExpectedTournamentsDtoFromDB().get(0);
        expectedTournamentDto.getUserDto().setPassword(null);
        expectedTournamentDto.getUserDto().setEmail(null);

        // when
        var actualTournamentDto = tournamentMapper.modelToDto(incomeTournament);

        // then
        assertThat(actualTournamentDto).usingRecursiveComparison().isEqualTo(expectedTournamentDto);
    }

}
