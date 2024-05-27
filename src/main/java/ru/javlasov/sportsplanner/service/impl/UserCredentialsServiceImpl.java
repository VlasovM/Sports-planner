package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.model.UserCredentials;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.UserCredentialsService;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    @Transactional(readOnly = true)
    public UserCredentials getCurrentAuthUser() {
        var emailCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return userCredentialsRepository.findByEmail(emailCurrentUser)
                .orElseThrow(() -> {
                    log.error("Ошибка при попытке найти пользователя по email %s".formatted(emailCurrentUser));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var authUser = userCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Ошибка при попытке найти пользователя по email %s".formatted(email));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        return User
                .builder()
                .username(authUser.getEmail())
                .password(authUser.getPassword())
                .roles(authUser.getRole().getRole())
                .build();
    }

}
