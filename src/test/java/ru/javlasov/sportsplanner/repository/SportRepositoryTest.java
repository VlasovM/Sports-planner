package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Sport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SportRepositoryTest {

    @Autowired
    private SportRepository sportRepository;

    @Test
    @DisplayName("Should get all sports")
    void getAllSportsTest() {
        // given
        Set<Sport> expectedSportsSet = ExpectedDataFromDB.getExpectedSportsFromDB();
        List<Sport> expectedSportsList = new ArrayList<>(expectedSportsSet);
        expectedSportsList = expectedSportsList
                .stream()
                .sorted(Comparator.comparing(Sport::getId)).
                collect(Collectors.toList());

        // when
        List<Sport> actualSports = sportRepository.findAll();

        // then
        assertThat(expectedSportsList.size()).isEqualTo(actualSports.size());
        assertThat(expectedSportsList.get(0).getId()).isEqualTo(actualSports.get(0).getId());
        assertThat(expectedSportsList.get(0).getTitle()).isEqualTo(actualSports.get(0).getTitle());
        assertThat(expectedSportsList.get(1).getId()).isEqualTo(actualSports.get(1).getId());
        assertThat(expectedSportsList.get(1).getTitle()).isEqualTo(actualSports.get(1).getTitle());
    }

    @Test
    @DisplayName("Should get sport by id")
    void getSportByIdTest() {
        // given
        var expectedSport = ExpectedDataFromDB.getExpectedSportsFromDB().stream()
                .filter(sport1 -> sport1.getId().equals(1L))
                .findFirst().orElseThrow();

        // when
        var actualSport = sportRepository.findById(1L);

        // then
        assertThat(actualSport).isPresent();
        assertThat(actualSport.get().getId()).isEqualTo(expectedSport.getId());
        assertThat(actualSport.get().getTitle()).isEqualTo(expectedSport.getTitle());
    }

}