package ru.javlasov.sportsplanner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.sportsplanner.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
