package ru.javlasov.planner.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.planner.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
