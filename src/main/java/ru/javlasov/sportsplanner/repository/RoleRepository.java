package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.sportsplanner.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
