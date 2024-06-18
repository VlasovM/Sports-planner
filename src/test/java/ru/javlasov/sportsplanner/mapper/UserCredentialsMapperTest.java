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
        var incomeUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);

        //when
        var actualUserDto = userCredentialsMapper.modelToDto(incomeUserCredentials);

        //then
        assertThat(actualUserDto).isNotNull();
        assertThat(expectedUserDto).usingRecursiveComparison().isEqualTo(actualUserDto);
    }
}