package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Tournament;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class TournamentRepositoryTest {

    @Autowired
    private TournamentRepository tournamentRepository;

    @DisplayName("Should get all tournaments")
    @Test
    void getAllTournamentsTest() {
        // given
        Set<Tournament> expectedTournamentsSet = ExpectedDataFromDB.getExpectedTournamentsFromDB();
        List<Tournament> expectedTournamentsList = new ArrayList<>(expectedTournamentsSet);
        expectedTournamentsList = expectedTournamentsList
                .stream()
                .sorted(Comparator.comparing(Tournament::getId)).
                collect(Collectors.toList());

        // when
        List<Tournament> actualTournaments = tournamentRepository.findAll();

        // then
        assertThat(expectedTournamentsList.size()).isEqualTo(actualTournaments.size());
        assertThat(expectedTournamentsList.get(0).getId()).isEqualTo(actualTournaments.get(0).getId());
        assertThat(expectedTournamentsList.get(0).getTitle()).isEqualTo(actualTournaments.get(0).getTitle());
        assertThat(expectedTournamentsList.get(1).getId()).isEqualTo(actualTournaments.get(1).getId());
        assertThat(expectedTournamentsList.get(1).getTitle()).isEqualTo(actualTournaments.get(1).getTitle());
    }

    @DisplayName("Should get tournament by id")
    @Test
    void getTournamentByIdTest() {
        // given
        var expectedTournament = ExpectedDataFromDB.getExpectedTournamentsFromDB().stream()
                .filter(tournament -> tournament.getId().equals(1L))
                .findFirst().orElseThrow();

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
        Set<Tournament> expectedTournamentsSet = ExpectedDataFromDB.getExpectedTournamentsFromDB();
        List<Tournament> expectedTournamentsList = new ArrayList<>(expectedTournamentsSet);
        expectedTournamentsList = expectedTournamentsList
                .stream()
                .sorted(Comparator.comparing(Tournament::getId)).
                collect(Collectors.toList());

        // when

        var actualTournament = tournamentRepository.findAllByUser(1L);

        // then
        assertThat(actualTournament).isNotEmpty();
        assertThat(expectedTournamentsList.get(0).getId()).isEqualTo(actualTournament.get(0).getId());
        assertThat(expectedTournamentsList.get(0).getTitle()).isEqualTo(actualTournament.get(0).getTitle());
    }

}