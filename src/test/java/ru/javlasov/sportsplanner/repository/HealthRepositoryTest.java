package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.Health;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class HealthRepositoryTest {

    @Autowired
    private HealthRepository healthRepository;

    @Test
    @DisplayName("Should get all healths")
    void getAllHealthsTest() {
        //given
        List<Health> expectedHealths = ExpectedDataFromDB.getExpectedHealthFromDB();

        //when
        List<Health> actualHealths = healthRepository.findAll();

        //then
        assertThat(expectedHealths.size()).isEqualTo(actualHealths.size());
        assertThat(expectedHealths.get(0).getId()).isEqualTo(actualHealths.get(0).getId());
        assertThat(expectedHealths.get(0).getDoctorFullName()).isEqualTo(actualHealths.get(0).getDoctorFullName());
        assertThat(expectedHealths.get(1).getId()).isEqualTo(actualHealths.get(1).getId());
        assertThat(expectedHealths.get(1).getClinic()).isEqualTo(actualHealths.get(1).getClinic());
    }

    @Test
    @DisplayName("Should get health by id")
    void getHealthByIdTest() {
        //given
        var expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB().get(0);

        //when
        var actualHealth = healthRepository.findById(1L);

        //then
        assertThat(actualHealth).isPresent();
        assertThat(actualHealth.get().getId()).isEqualTo(expectedHealth.getId());
        assertThat(actualHealth.get().getDoctorFullName()).isEqualTo(expectedHealth.getDoctorFullName());
        assertThat(actualHealth.get().getDate()).isEqualTo(expectedHealth.getDate());
    }

}