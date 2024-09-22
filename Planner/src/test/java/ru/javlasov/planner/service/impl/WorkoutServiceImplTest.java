package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.dto.WorkoutDto;
import ru.javlasov.planner.mapper.WorkoutMapper;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.model.Workout;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.repository.WorkoutRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;
import ru.javlasov.planner.service.WorkoutService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

class WorkoutServiceImplTest {

    private final WorkoutRepository mockWorkoutRepository = Mockito.mock(WorkoutRepository.class);

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final WorkoutMapper mockWorkoutMapper = Mockito.mock(WorkoutMapper.class);

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final UserCredentialsRepository mockUserCredentialsRepository
            = Mockito.mock(UserCredentialsRepository.class);

    private final WorkoutService underTestService = new WorkoutServiceImpl(mockWorkoutRepository,
            mockUserCredentialsService, mockWorkoutMapper, mockLoggingService);

    @Test
    @DisplayName("Should get all workout for current user")
    void getAllWorkoutTest() {
        // given
        List<Workout> expectedWorkout = ExpectedDataFromDB.getExpectedWorkoutFromDB();
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUser = expectedUserCredentials.getUser();

        var expectedWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB();

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockWorkoutRepository.findAllByUser(expectedUser)).thenReturn(expectedWorkout);
        Mockito.when(mockWorkoutMapper.modelListToDtoList(expectedWorkout)).thenReturn(expectedWorkoutDto);

        List<WorkoutDto> actualWorkoutDto = underTestService.getAllForCurrentUser();

        // then
        assertThat(actualWorkoutDto.size()).isEqualTo(expectedWorkoutDto.size());
        assertThat(actualWorkoutDto).usingRecursiveComparison().isEqualTo(expectedWorkoutDto);
    }

    @Test
    @DisplayName("Should update exist workout")
    void updateWorkoutTest() {
        // given
        var expectedWorkoutDto = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);
        expectedWorkoutDto.setTitle("OtherTitle");
        expectedWorkoutDto.setReflection("OtherReflection");

        var expectedWorkout = ExpectedDataFromDB.getExpectedWorkoutFromDB().get(0);
        expectedWorkout.setTitle(expectedWorkoutDto.getTitle());
        expectedWorkout.setReflection(expectedWorkoutDto.getReflection());

        var incomeWorkout = ExpectedDataFromDB.getExpectedWorkoutDtoFromDB().get(0);

        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        // when
        makeMockAuthUser(expectedUserCredentials);

        Mockito.when(mockWorkoutMapper.dtoToModel(any())).thenReturn(expectedWorkout);
        Mockito.when(mockWorkoutRepository.save(any())).thenReturn(expectedWorkout);
        Mockito.when(mockWorkoutRepository.findById(incomeWorkout.getId())).thenReturn(Optional.of(expectedWorkout));
        Mockito.when(mockWorkoutMapper.modelToDto(expectedWorkout)).thenReturn(expectedWorkoutDto);

        underTestService.updateOrCreate(incomeWorkout);
        var actualWorkoutDto = underTestService.getById(incomeWorkout.getId());

        // then
        assertThat(actualWorkoutDto).usingRecursiveComparison().isEqualTo(expectedWorkoutDto);
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