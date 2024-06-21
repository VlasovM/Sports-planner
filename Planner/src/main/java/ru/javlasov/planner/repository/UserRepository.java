package ru.javlasov.planner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.planner.model.User;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
