package ru.javlasov.planner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.model.Health;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class HealthRepositoryTest {

    @Autowired
    private HealthRepository healthRepository;

    @Test
    @DisplayName("Should get all health by user id")
    void findAllByUserTest() {
        // given
        List<Health> expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB();
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        // when

        var actualHealth = healthRepository.findAllByUser(expectedUser);

        // then
        assertThat(actualHealth).isNotEmpty();
        assertThat(expectedHealth).usingRecursiveComparison().isEqualTo(actualHealth);
    }

}
