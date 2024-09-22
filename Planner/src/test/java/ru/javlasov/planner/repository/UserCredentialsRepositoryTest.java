package ru.javlasov.planner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.planner.ExpectedDataFromDB;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserCredentialsRepositoryTest {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Test
    @DisplayName("Should find userCredentials by email")
    void findByEmailTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        // when
        var actualUserCredentials = userCredentialsRepository
                .findByEmail(expectedUserCredentials.getEmail());

        // then
        assertThat(actualUserCredentials).isPresent();
        assertThat(expectedUserCredentials).usingRecursiveComparison().isEqualTo(actualUserCredentials.get());
    }

    @Test
    @DisplayName("Should find userCredentials by user id")
    void findUserByUserIdTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        // when
        var actualUserCredentials = userCredentialsRepository
                .findUserByUserId(expectedUserCredentials.getUser().getId());

        // then
        assertThat(actualUserCredentials).isPresent();
        assertThat(expectedUserCredentials).usingRecursiveComparison().isEqualTo(actualUserCredentials.get());
    }

}