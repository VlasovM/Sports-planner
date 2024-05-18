package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Health;

import java.util.List;

public interface HealthRepository extends ListCrudRepository<Health, Long> {

    List<Health> findAllByUser(Long userId);

}
