package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.User;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
