package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

class UserCredentialsServiceImplTest {

    private final UserCredentialsRepository mockUserCredentialsRepository =
            Mockito.mock(UserCredentialsRepository.class);

    private final LoggingService mockLoggingService =
            Mockito.mock(LoggingService.class);

    private final UserCredentialsService underTestService =
            new UserCredentialsServiceImpl(mockUserCredentialsRepository, mockLoggingService);

    @Test
    @DisplayName("Should get current auth user")
    void getCurrentAuthUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.of(expectedUserCredentials));

        // when
        var actualUserCredentials = underTestService.getCurrentAuthUser();

        // then
        assertThat(actualUserCredentials).isNotNull();
        assertThat(expectedUserCredentials.getEmail()).isEqualTo(actualUserCredentials.getEmail());
        assertThat(expectedUserCredentials.getUser()).isEqualTo(actualUserCredentials.getUser());
    }

    @Test
    @DisplayName("Should get error when user from email")
    void getExceptionWhenFindCurrentUserTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.empty());

        // when
        NotFoundException actualException = assertThrows(NotFoundException.class, underTestService::getCurrentAuthUser);

        // then
        assertThat(actualException).isNotNull();
        assertThat(actualException.getMessage())
                .isEqualTo("Пользователь с такой почтой не найден!");
    }

    @Test
    @DisplayName("Should get user details by email")
    void loadUserByUsername() {
        // given
        var expectedUser = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));
        User
                .builder()
                .username(expectedUser.getEmail())
                .password(expectedUser.getPassword())
                .roles(expectedUser.getRole().getRole())
                .build();

        // when
        var actualUserDetails = underTestService.loadUserByUsername(expectedUser.getEmail());

        // then
        assertThat(actualUserDetails).isNotNull();
        assertThat(actualUserDetails.getUsername()).isEqualTo(expectedUser.getEmail());
        assertThat(actualUserDetails.getPassword()).isEqualTo(expectedUser.getPassword());
    }

}