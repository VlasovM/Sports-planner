package ru.javlasov.sportsplanner.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class UserCredentialsMapperTest {

    @Autowired
    private UserCredentialsMapper userCredentialsMapper;

    @Test
    @DisplayName("Should convert userCredentials to UserDto")
    void modelToDtoTest() {
        //given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        //when
        var actualUserDto = userCredentialsMapper.modelToDto(expectedUserCredentials);

        //then
        assertThat(actualUserDto).isNotNull();
        assertThat(expectedUserCredentials.getUser().getName()).isEqualTo(actualUserDto.getName());
        assertThat(expectedUserCredentials.getUser().getSurname()).isEqualTo(actualUserDto.getSurname());
        assertThat(expectedUserCredentials.getEmail()).isEqualTo(actualUserDto.getEmail());
        assertThat(expectedUserCredentials.getUser().getId()).isEqualTo(actualUserDto.getId());
        assertThat(actualUserDto.getPassword()).isNull();
    }
}