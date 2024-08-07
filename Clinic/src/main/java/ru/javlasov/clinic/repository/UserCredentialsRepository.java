package ru.javlasov.clinic.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.clinic.model.UserCredentials;

import java.util.Optional;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {

    @EntityGraph(value = "role-doctor-entity-graph")
    Optional<UserCredentials> findByEmail(@NonNull String email);

    @EntityGraph(value = "role-doctor-entity-graph")
    Optional<UserCredentials> findUserByDoctorId(@NonNull Long userId);

}
