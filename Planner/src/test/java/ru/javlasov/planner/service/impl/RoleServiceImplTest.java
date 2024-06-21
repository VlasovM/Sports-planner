package ru.javlasov.planner.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.javlasov.planner.ExpectedDataFromDB;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.repository.RoleRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.RoleService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class RoleServiceImplTest {

    private final RoleRepository mockRoleRepo = Mockito.mock(RoleRepository.class);

    private final LoggingService mockLoggingService = Mockito.mock(LoggingService.class);

    private final RoleService underTestService = new RoleServiceImpl(mockRoleRepo, mockLoggingService);

    @Test
    @DisplayName("Should get user role")
    void getUserRoleTest() {
        // given
        var expectedUserRole = ExpectedDataFromDB.getExpectedRolesFromDB().get(0);

        // when
        Mockito.when(mockRoleRepo.findById(1L)).thenReturn(Optional.of(expectedUserRole));
        var actualUserRole = underTestService.getUserRole();

        // then
        assertThat(actualUserRole).usingRecursiveComparison().isEqualTo(expectedUserRole);
    }

    @Test
    @DisplayName("Should get exception when role not found")
    void getExceptionWhenNotFoundRoleTest() {
        // given

        // when
        Mockito.when(mockRoleRepo.findById(any())).thenReturn(Optional.empty());
        NotFoundException actualResult = assertThrows(NotFoundException.class, underTestService::getUserRole);

        // then
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getMessage()).isEqualTo("Не найдена роль с id = 1");
    }

}
