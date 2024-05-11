package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Health;

public interface HealthRepository extends ListCrudRepository<Health, Long> {
}
