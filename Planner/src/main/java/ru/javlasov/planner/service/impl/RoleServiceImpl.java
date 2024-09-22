package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.model.Role;
import ru.javlasov.planner.repository.RoleRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final Long ROLE_USER_ID = 1L;

    private final RoleRepository roleRepository;

    private final LoggingService loggingService;

    @Override
    public Role getUserRole() {
        return roleRepository.findById(ROLE_USER_ID).orElseThrow(
                () -> {
                    var errorMessage = "Не найдена роль с id = %d".formatted(ROLE_USER_ID);
                    sendMessage(errorMessage);
                    throw new NotFoundException(errorMessage);
                }
        );
    }

    private void sendMessage(String message) {
        var loggingDto = new LoggerEvent(message, TypeMessage.ERROR);
        loggingService.sendMessage(loggingDto);
    }

}
