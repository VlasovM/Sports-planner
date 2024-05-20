package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.UserCredentialsMapper;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.repository.UserRepository;
import ru.javlasov.sportsplanner.service.UserCredentialsService;
import ru.javlasov.sportsplanner.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    private final UserCredentialsRepository mockUserCredentialsRepository =
            Mockito.mock(UserCredentialsRepository.class);

    private final UserRepository mockUserRepository = Mockito.mock(UserRepository.class);

    private final UserCredentialsMapper mockUserCredentialsMapper = Mockito.mock(UserCredentialsMapper.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final UserService underTestService = new UserServiceImpl(mockUserCredentialsRepository, mockUserRepository,
            mockUserCredentialsMapper, mockUserCredentialsService);

    @Test
    @DisplayName("Should get user dto")
    void getInfoAuthorizedUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUserDto = ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0);
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
        Mockito.when(mockUserCredentialsMapper.modelToDto(expectedUserCredentials)).thenReturn(expectedUserDto);

        // when
        var actualUserDto = underTestService.getInfoAuthorizedUser();

        // then
        assertThat(expectedUserDto.getId()).isEqualTo(actualUserDto.getId());
        assertThat(expectedUserDto.getName()).isEqualTo(actualUserDto.getName());
        assertThat(expectedUserDto.getEmail()).isEqualTo(actualUserDto.getEmail());
    }

    @Test
    @DisplayName("Should edit user info")
    void editProfileTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUserAfterSave = expectedUserCredentials.getUser();
        var expectedUserDto = ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0);

        Mockito.when(mockUserCredentialsRepository.findUserByUserId(expectedUserCredentials.getId())).thenReturn(
                Optional.of(expectedUserCredentials));
        expectedUserAfterSave.setName("OtherNameForUser");
        expectedUserAfterSave.setAge(18);
        expectedUserDto.setName(expectedUserDto.getName());
        expectedUserDto.setAge(expectedUserAfterSave.getAge());
        Mockito.when(mockUserRepository.save(expectedUserAfterSave)).thenReturn(expectedUserAfterSave);
        Mockito.when(mockUserCredentialsMapper.modelToDto(expectedUserCredentials)).thenReturn(expectedUserDto);

        // when
        underTestService.editProfile(expectedUserDto);
//
//        // then
//        assertThat(actualUserDto).isNotNull();
//        assertThat(actualUserDto.getName()).isEqualTo(expectedUserDto.getName());
//        assertThat(actualUserDto.getAge()).isEqualTo(expectedUserDto.getAge());
    }

    @Test
    @DisplayName("Should get exception if user not exists")
    void getExceptionWhenEditNotExistsUserTest() {
        // given
        Mockito.when(mockUserCredentialsRepository.findUserByUserId(1L)).thenReturn(Optional.empty());

        // when
        NotFoundException actualException = assertThrows(NotFoundException.class, () ->
                underTestService.editProfile(ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0)));

        // then
        assertThat(actualException).isNotNull();
        assertThat(actualException.getMessage()).isEqualTo("Не найден пользователь с id = 1");

    }

}