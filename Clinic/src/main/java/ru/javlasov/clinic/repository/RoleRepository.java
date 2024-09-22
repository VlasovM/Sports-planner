package ru.javlasov.clinic.repository;

import org.springframework.data.repository.CrudRepository;
import ru.javlasov.clinic.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
