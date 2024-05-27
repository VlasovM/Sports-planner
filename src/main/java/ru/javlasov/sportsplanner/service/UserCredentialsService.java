package ru.javlasov.sportsplanner.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.javlasov.sportsplanner.model.UserCredentials;

public interface UserCredentialsService extends UserDetailsService {

    UserCredentials getCurrentAuthUser();

}
