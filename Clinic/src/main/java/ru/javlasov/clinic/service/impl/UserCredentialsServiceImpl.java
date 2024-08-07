package ru.javlasov.clinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javlasov.clinic.model.UserCredentials;
import ru.javlasov.clinic.repository.UserCredentialsRepository;
import ru.javlasov.clinic.service.UserCredentialsService;

@Service
@RequiredArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    @Transactional(readOnly = true)
    public UserCredentials getCurrentAuthUser() {
        var emailCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserCredentials(emailCurrentUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        var authUser = getUserCredentials(email);
        return User.builder().username(authUser.getEmail()).password(authUser.getPassword()).roles(authUser.getRole().getRole()).build();
    }

    private UserCredentials getUserCredentials(String email) {
        return userCredentialsRepository.findByEmail(email).orElseThrow(() -> {
            throw new RuntimeException("Пользователь с такой почтой не найден!");
        });
    }

}
