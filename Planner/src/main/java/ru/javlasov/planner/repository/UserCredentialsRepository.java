package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.planner.model.UserCredentials;

import java.util.Optional;

public interface UserCredentialsRepository extends ListCrudRepository<UserCredentials, Long> {

    @EntityGraph(value = "role-user-entity-graph")
    Optional<UserCredentials> findByEmail(@NonNull String email);

    @EntityGraph(value = "role-user-entity-graph")
    Optional<UserCredentials> findUserByUserId(@NonNull Long userId);

}
