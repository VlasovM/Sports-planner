package ru.javlasov.clinic.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.javlasov.clinic.model.UserCredentials;

public interface UserCredentialsService extends UserDetailsService {

    UserCredentials getCurrentAuthUser();

}
