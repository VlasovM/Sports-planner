package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Should get user dto")
    void getInfoAuthorizedUserTest() {
        //given
        var expectedUserDto = ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("test@mail.ru");
        SecurityContextHolder.setContext(securityContext);

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());

        //when
        var actualUserDto = userService.getInfoAuthorizedUser();

        //then
        assertThat(expectedUserDto.getId()).isEqualTo(actualUserDto.getId());
        assertThat(expectedUserDto.getName()).isEqualTo(actualUserDto.getName());
        assertThat(expectedUserDto.getEmail()).isEqualTo(actualUserDto.getEmail());
    }

    @Test
    @DisplayName("Should get exception, if not found user by email")
    void getErrorWhenUserNotFoundByEmailTest() {
        //given
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn("notExists@mail.ru");
        SecurityContextHolder.setContext(securityContext);


        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> userService.getInfoAuthorizedUser());

        //then
        assertThat(notFoundException.getMessage()).isEqualTo("Возникла ошибка с получением данных," +
                " обратитесь к администратору системы.");
    }

    @Test
    @DisplayName("Should edit user info")
    void editProfileTest() {
        //given
        var expectedUserDto = ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0);
        expectedUserDto.setSurname("newSurName");

        //when
        var actualUserDto = userService.editProfile(expectedUserDto);

        //then
        assertThat(actualUserDto.getName()).isEqualTo(actualUserDto.getName());
        assertThat(actualUserDto.getSurname()).isEqualTo(actualUserDto.getSurname());
    }

    @Test
    @DisplayName("Should create new user")
    void createProfileTest() {
        //given
        var incomeUserDto = ExpectedDataFromDB.getExpectedUserDtoFromDB().get(0);
        incomeUserDto.setEmail("newEmail@mail.ru");
        incomeUserDto.setId(null);
        incomeUserDto.setName("name");

        //when
        userService.createProfile(incomeUserDto);
        var actualUser = userRepository.findById(3L);

        //then
        assertThat(actualUser).isPresent();
        assertThat(actualUser.get().getName()).isEqualTo(incomeUserDto.getName());
        assertThat(actualUser.get().getSurname()).isEqualTo(incomeUserDto.getSurname());
        assertThat(actualUser.get().getAge()).isEqualTo(incomeUserDto.getAge());
        assertThat(actualUser.get().getBirthday()).isEqualTo(incomeUserDto.getBirthday());
    }

}