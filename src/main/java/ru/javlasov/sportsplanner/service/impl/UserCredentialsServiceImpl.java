package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.dto.LoggerEvent;
import ru.javlasov.sportsplanner.enums.TypeMessage;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.LoggingService;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    private final LoggingService loggingService;

    @Override
    @Transactional(readOnly = true)
    public UserCredentials getCurrentAuthUser() {
        var emailCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserCredentials(emailCurrentUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var authUser = getUserCredentials(email);
        return User
                .builder()
                .username(authUser.getEmail())
                .password(authUser.getPassword())
                .roles(authUser.getRole().getRole())
                .build();
    }

    private UserCredentials getUserCredentials(String email) {
        return userCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> {
                    sendMessage("Ошибка при попытке найти пользователя по email %s".formatted(email));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

    private void sendMessage(String message) {
        var loggingDto = new LoggerEvent(message, TypeMessage.ERROR);
        loggingService.sendMessage(loggingDto);
    }

}
