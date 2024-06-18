package ru.javlasov.sportsplanner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.sportsplanner.ExpectedDataFromDB;
import ru.javlasov.sportsplanner.dto.HealthDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.mapper.HealthMapper;
import ru.javlasov.sportsplanner.model.Health;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.HealthRepository;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.HealthService;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HealthServiceImplTest {

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final HealthMapper mockHealthMapper = Mockito.mock(HealthMapper.class);

    private final HealthRepository mockHealthRepository = Mockito.mock(HealthRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final UserCredentialsRepository mockUserCredentialsRepository
            = Mockito.mock(UserCredentialsRepository.class);

    private final HealthService underTestService = new HealthServiceImpl(mockHealthMapper, mockHealthRepository,
            mockUserCredentialsService, mockLoggingService);

    @Test
    @DisplayName("Should get health for current user")
    void getAllHeathTest() {
        // given
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUser = expectedUserCredentials.getUser();

        List<HealthDto> expectedHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB();
        List<Health> expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB();

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockHealthRepository.findAllByUser(expectedUser)).thenReturn(expectedHealth);
        Mockito.when(mockHealthMapper.modelListToDtoList(expectedHealth)).thenReturn(expectedHealthDto);

        List<HealthDto> actualHealthDto = underTestService.getHealthCurrentUser();

        // then
        assertThat(actualHealthDto.size()).isEqualTo(expectedHealthDto.size());
        assertThat(actualHealthDto).usingRecursiveComparison().isEqualTo(expectedHealthDto);
    }

    @Test
    @DisplayName("Should get exception when don't find health by user id")
    void deleteByIdNotExistsHealthTest() {
        // given
        Mockito.when(mockHealthRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        NotFoundException actualException = assertThrows(NotFoundException.class,
                () -> underTestService.deleteById(1L));

        // then
        assertThat(actualException).isNotNull();
        assertThat(actualException.getClass()).isEqualTo(NotFoundException.class);
        assertThat(actualException.getMessage()).isEqualTo("Возникла ошибка с получением данных, " +
                "обратитесь к администратору системы.");
    }

    private void makeMockAuthUser(UserCredentials expectedUserCredentials) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepository.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
    }

}