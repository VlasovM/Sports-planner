package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Tests for tournament repository")
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
