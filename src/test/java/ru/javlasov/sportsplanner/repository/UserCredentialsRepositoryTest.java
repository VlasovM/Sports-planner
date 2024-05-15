package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserCredentialsRepositoryTest {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Test
    @DisplayName("Should find userCredentials by email")
    void findByEmailTest() {
        //given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        //when
        var actualUserCredentials = userCredentialsRepository
                .findByEmail(expectedUserCredentials.getEmail());

        //then
        assertThat(actualUserCredentials).isPresent();
        assertThat(actualUserCredentials.get().getEmail()).isEqualTo(expectedUserCredentials.getEmail());
        assertThat(actualUserCredentials.get().getUser()).isNotNull();
        assertThat(actualUserCredentials.get().getUser().getId()).isEqualTo(expectedUserCredentials.getUser().getId());
        assertThat(actualUserCredentials.get().getUser().getName())
                .isEqualTo(expectedUserCredentials.getUser().getName());
    }

    @Test
    @DisplayName("Should find userCredentials by user id")
    void findUserByUserIdTest() {
        //given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        //when
        var actualUserCredentials = userCredentialsRepository
                .findUserByUserId(expectedUserCredentials.getUser().getId());

        //then
        assertThat(actualUserCredentials).isPresent();
        assertThat(actualUserCredentials.get().getEmail()).isEqualTo(expectedUserCredentials.getEmail());
        assertThat(actualUserCredentials.get().getUser()).isNotNull();
        assertThat(actualUserCredentials.get().getUser().getId()).isEqualTo(expectedUserCredentials.getUser().getId());
        assertThat(actualUserCredentials.get().getUser().getName())
                .isEqualTo(expectedUserCredentials.getUser().getName());
    }
}