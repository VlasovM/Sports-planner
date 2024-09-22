package ru.javlasov.planner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.model.Tournament;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    @DisplayName("Should get all health by user id")
    void findAllByUserTest() {
        // given
        List<Tournament> expectedTournaments = ExpectedDataFromDB.getExpectedTournamentsFromDB();
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        // when
        var actualTournaments = tournamentRepository.findAllByUser(expectedUser);

        // then
        assertThat(actualTournaments).isNotEmpty();
        assertThat(expectedTournaments).usingRecursiveComparison().isEqualTo(actualTournaments);
    }

}
