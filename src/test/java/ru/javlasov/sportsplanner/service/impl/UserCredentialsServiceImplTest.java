package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserCredentialsServiceImplTest {

    private final UserCredentialsRepository mockUserCredentialsRepository =
            Mockito.mock(UserCredentialsRepository.class);

    private final UserCredentialsService underTestService =
            new UserCredentialsServiceImpl(mockUserCredentialsRepository);

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
                .isEqualTo("Возникла ошибка с получением данных, обратитесь к администратору системы.");
    }

}