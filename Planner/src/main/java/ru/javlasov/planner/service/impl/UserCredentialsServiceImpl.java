package ru.javlasov.planner.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.planner.dto.LoggerEvent;
import ru.javlasov.planner.enums.TypeMessage;
import ru.javlasov.planner.expection.NotFoundException;
import ru.javlasov.planner.model.UserCredentials;
import ru.javlasov.planner.repository.UserCredentialsRepository;
import ru.javlasov.planner.service.LoggingService;
import ru.javlasov.planner.service.UserCredentialsService;

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
                    throw new NotFoundException("Пользователь с такой почтой не найден!");
                });
    }

    private void sendMessage(String message) {
        var loggingDto = new LoggerEvent(message, TypeMessage.ERROR);
        loggingService.sendMessage(loggingDto);
    }

}
