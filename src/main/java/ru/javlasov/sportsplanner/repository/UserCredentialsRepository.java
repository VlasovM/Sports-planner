package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.sportsplanner.model.UserCredentials;

import java.util.Optional;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {

    Optional<UserCredentials> findByEmail(String email);

}
