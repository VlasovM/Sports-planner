package ru.javlasov.sportsplanner.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("User repository test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get all users")
    void getAllUsersTest() {
        //given
        List<User> expectedUsers = ExpectedDataFromDB.getExpectedUsersFromDB();

        //when
        List<User> actualUsers = userRepository.findAll();

        //then
        assertThat(expectedUsers.size()).isEqualTo(actualUsers.size());
        assertThat(expectedUsers.get(0).getId()).isEqualTo(actualUsers.get(0).getId());
        assertThat(expectedUsers.get(0).getName()).isEqualTo(actualUsers.get(0).getName());
        assertThat(expectedUsers.get(1).getId()).isEqualTo(actualUsers.get(1).getId());
        assertThat(expectedUsers.get(1).getName()).isEqualTo(actualUsers.get(1).getName());
    }

    @Test
    @DisplayName("Should get User by id")
    void getUserByIdTest() {
        // given
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        //when
        var actualUser = userRepository.findById(1L);

        //then
        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getId()).isEqualTo(expectedUser.getId());
        assertThat(actualUser.get().getName()).isEqualTo(expectedUser.getName());
        assertThat(actualUser.get().getTrains().size()).isEqualTo(expectedUser.getTrains().size());
    }

}