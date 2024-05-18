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
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @DisplayName("Should get all tournaments")
    @Test
    void getAllTournamentsTest() {
        // given
        List<Tournament> expectedTournaments = ExpectedDataFromDB.getExpectedTournamentsFromDB();

        // when
        List<Tournament> actualTournaments = tournamentRepository.findAll();

        // then
        assertThat(expectedTournaments.size()).isEqualTo(actualTournaments.size());
        assertThat(expectedTournaments.get(0).getId()).isEqualTo(actualTournaments.get(0).getId());
        assertThat(expectedTournaments.get(0).getTitle()).isEqualTo(actualTournaments.get(0).getTitle());
        assertThat(expectedTournaments.get(1).getId()).isEqualTo(actualTournaments.get(1).getId());
        assertThat(expectedTournaments.get(1).getTitle()).isEqualTo(actualTournaments.get(1).getTitle());
    }

    @DisplayName("Should get tournament by id")
    @Test
    void getTournamentByIdTest() {
        // given
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().get(0);

        // when
        var actualTournament = tournamentRepository.findById(1L);

        // then
        assertThat(actualTournament).isPresent();
        assertThat(actualTournament.get().getId()).isEqualTo(expectedTournament.getId());
        assertThat(actualTournament.get().getTitle()).isEqualTo(expectedTournament.getTitle());
    }

    @Test
    @DisplayName("Should get all tournaments by user id")
    void findAllByUserTest() {
        // given
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB();

        // when

        var actualTournament = tournamentRepository.findAllByUser(1L);

        // then
        assertThat(actualTournament).isNotEmpty();
        assertThat(expectedTournament.get(0).getId()).isEqualTo(actualTournament.get(0).getId());
        assertThat(expectedTournament.get(0).getTitle()).isEqualTo(actualTournament.get(0).getTitle());
    }

}