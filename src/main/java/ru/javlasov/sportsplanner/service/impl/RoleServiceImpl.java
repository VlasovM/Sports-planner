package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.model.Role;
import ru.javlasov.sportsplanner.repository.RoleRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.RoleService;

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
