package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.mapper.UserCredentialsMapper;
import ru.javlasov.planner.mapper.UserMapper;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.repository.UserRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.RoleService;
import ru.javlasov.planner.service.UserCredentialsService;
import ru.javlasov.planner.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;

public class UserServiceImplTest {

    private final UserCredentialsService mockUserCredentialsService = Mockito.mock(UserCredentialsService.class);

    private final UserCredentialsRepository mockUserCredentialsRepo = Mockito.mock(UserCredentialsRepository.class);

    private final UserRepository mockUserRepo = Mockito.mock(UserRepository.class);

    private final UserCredentialsMapper mockUserCredentialsMapper = Mockito.mock(UserCredentialsMapper.class);

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final RoleService mockRoleService = Mockito.mock(RoleService.class);

    private final UserMapper mockUserMapper = Mockito.mock(UserMapper.class);

    private final UserService underTestService = new UserServiceImpl(mockUserCredentialsRepo, mockUserRepo,
            mockUserCredentialsMapper, mockUserCredentialsService, mockLoggingService, mockRoleService, mockUserMapper);

    @Test
    @DisplayName("Should get all info for current user")
    void getInfoForCurrentUserTest() {
        // given
        var expectedUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);

        // when
        makeMockAuthUser(expectedUserCredentials);
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
        Mockito.when(mockUserCredentialsMapper.modelToDto(expectedUserCredentials)).thenReturn(expectedUserDto);

        var actualUserDto = underTestService.getInfoAuthorizedUser();

        // then
        assertThat(actualUserDto).usingRecursiveComparison().isEqualTo(expectedUserDto);

    }

    @Test
    @DisplayName("Should create user")
    void createProfileTest() {
        // given
        var expectedUserRole = ExpectedDataFromDB.getExpectedRolesFromDB().get(0);
        var expectedUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);
        var incomeUserDto = ExpectedDataFromDB.getExpectedUsersDtoFromDB().get(0);
        incomeUserDto.setId(null);
        var incomeUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);
        incomeUser.setId(null);
        var incomeUserCredentials = ExpectedDataFromDB.getExpectedUserCredentialsFromDB().get(0);
        incomeUserCredentials.setId(null);


        // when
        Mockito.when(mockRoleService.getUserRole()).thenReturn(expectedUserRole);
        Mockito.when(mockUserMapper.dtoToModel(incomeUserDto)).thenReturn(incomeUser);
        Mockito.when(mockUserCredentialsRepo.save(any())).thenReturn(expectedUserCredentials);
        underTestService.createProfile(incomeUserDto);

        // then

    }

    @Test
    @DisplayName("Should get use by id")
    void getUserByIdTest() {
        // given
        var incomeId = 1L;
        var expectedUser = ExpectedDataFromDB.getExpectedUsersFromDB().get(0);

        // when
        Mockito.when(mockUserRepo.findById(incomeId)).thenReturn(Optional.of(expectedUser));
        var actualUser = underTestService.getUserById(incomeId);

        // then
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUser);
    }

    @Test
    @DisplayName("Should get exception when user not found")
    void getUserByNotExistsIdTest() {
        // given
        var incomeId = 10L;

        // when
        Mockito.when(mockUserRepo.findById(incomeId)).thenReturn(Optional.empty());
        NotFoundException actualResult = assertThrows(NotFoundException.class, () -> underTestService.getUserById(incomeId));


        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getMessage()).isEqualTo("Не найден пользователь с id = 10");
    }


    private void makeMockAuthUser(UserCredentials expectedUserCredentials) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(securityContext.getAuthentication().getName()).thenReturn(expectedUserCredentials.getEmail());
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(mockUserCredentialsRepo.findByEmail(expectedUserCredentials.getEmail()))
                .thenReturn(Optional.empty());
        Mockito.when(mockUserCredentialsService.getCurrentAuthUser()).thenReturn(expectedUserCredentials);
    }

}
