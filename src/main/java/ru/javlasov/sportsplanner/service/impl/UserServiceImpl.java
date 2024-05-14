package ru.javlasov.sportsplanner.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.javlasov.sportsplanner.dto.UserDto;
import ru.javlasov.sportsplanner.expection.NotFoundException;
import ru.javlasov.sportsplanner.repository.UserCredentialsRepository;
import ru.javlasov.sportsplanner.service.UserService;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDto getInfoAuthorizedUser() {
        var emailCurrentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        var currentUser = userCredentialsRepository.findByEmail(emailCurrentUser)
                .orElseThrow(() ->  {
                    log.error("Ошибка при попытке найти пользователя по email %s".formatted(emailCurrentUser));
                    throw new NotFoundException("Возникла ошибка с получением данных," +
                            " обратитесь к администратору системы.");
                });
        //TODO: Доделать тут метод
        return null;
    }

}
