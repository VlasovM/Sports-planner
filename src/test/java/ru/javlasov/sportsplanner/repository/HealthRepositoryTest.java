package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Health;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HealthRepositoryTest {

    @Autowired
    private HealthRepository healthRepository;

    @Test
    @DisplayName("Should get all healths")
    void getAllHealthsTest() {
        // given
        Set<Health> expectedHealthsSet = ExpectedDataFromDB.getExpectedHealthFromDB();
        List<Health> expectedHealthsList = new ArrayList<>(expectedHealthsSet);
        expectedHealthsList = expectedHealthsList
                .stream()
                .sorted(Comparator.comparing(Health::getId)).
                collect(Collectors.toList());

        // when
        List<Health> actualHealths = healthRepository.findAll();

        // then
        assertThat(expectedHealthsList.size()).isEqualTo(actualHealths.size());
        assertThat(expectedHealthsList.get(0).getId()).isEqualTo(actualHealths.get(0).getId());
        assertThat(expectedHealthsList.get(0).getDoctorFullName()).isEqualTo(actualHealths.get(0).getDoctorFullName());
        assertThat(expectedHealthsList.get(1).getId()).isEqualTo(actualHealths.get(1).getId());
        assertThat(expectedHealthsList.get(1).getClinic()).isEqualTo(actualHealths.get(1).getClinic());
    }

    @Test
    @DisplayName("Should get health by id")
    void getHealthByIdTest() {
        // given
        var expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB().stream()
                .filter(health -> health.getId().equals(1L))
                .findFirst().orElseThrow();

        // when
        var actualHealth = healthRepository.findById(1L);

        // then
        assertThat(actualHealth).isPresent();
        assertThat(actualHealth.get().getId()).isEqualTo(expectedHealth.getId());
        assertThat(actualHealth.get().getDoctorFullName()).isEqualTo(expectedHealth.getDoctorFullName());
        assertThat(actualHealth.get().getDate()).isEqualTo(expectedHealth.getDate());
    }

    @Test
    @DisplayName("Should get all health by user id")
    void findAllByUserTest() {
        // given
        Set<Health> expectedHealthsSet = ExpectedDataFromDB.getExpectedHealthFromDB();
        List<Health> expectedHealthsList = new ArrayList<>(expectedHealthsSet);
        expectedHealthsList = expectedHealthsList
                .stream()
                .sorted(Comparator.comparing(Health::getId)).
                collect(Collectors.toList());

        // when
        var actualHearth = healthRepository.findAllByUser(1L);

        // then
        assertThat(actualHearth).isNotEmpty();
        assertThat(expectedHealthsList.get(0).getId()).isEqualTo(actualHearth.get(0).getId());
        assertThat(expectedHealthsList.get(0).getDoctorFullName()).isEqualTo(actualHearth.get(0).getDoctorFullName());
        assertThat(expectedHealthsList.get(0).getDoctorSpecialization())
                .isEqualTo(actualHearth.get(0).getDoctorSpecialization());
        assertThat(expectedHealthsList.get(0).getUser()).isEqualTo(actualHearth.get(0).getUser());
    }

}