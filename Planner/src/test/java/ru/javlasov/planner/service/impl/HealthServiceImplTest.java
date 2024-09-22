package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.dto.HealthDto;
import ru.javlasov.planner.mapper.HealthMapper;
import ru.javlasov.planner.model.Health;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.repository.HealthRepository;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.service.HealthService;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("Should get health by id")
    void getByIdTest() {
        // given
        var expectedHealth = ExpectedDataFromDB.getExpectedHealthFromDB().get(0);
        var expectedHealthDto = ExpectedDataFromDB.getExpectedHealthDtoFromDB().get(0);
        var incomeId = expectedHealth.getId();

        // when
        Mockito.when(mockHealthRepository.findById(incomeId)).thenReturn(Optional.of(expectedHealth));
        Mockito.when(mockHealthMapper.modelToDto(expectedHealth)).thenReturn(expectedHealthDto);
        var actualHealthDto = underTestService.getById(incomeId);

        // then
        assertThat(actualHealthDto).usingRecursiveComparison().isEqualTo(expectedHealthDto);
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