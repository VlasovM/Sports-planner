package ru.javlasov.planner.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.javlasov.planner.model.UserCredentials;

public interface UserCredentialsService extends UserDetailsService {

    UserCredentials getCurrentAuthUser();

}
