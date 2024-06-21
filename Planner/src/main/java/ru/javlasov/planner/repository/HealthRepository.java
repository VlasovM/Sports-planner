package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.planner.model.Health;
import ru.javlasov.planner.model.User;

import java.util.List;

public interface HealthRepository extends ListCrudRepository<Health, Long> {

    @NonNull
    @EntityGraph(value = "health-entity-graph")
    List<Health> findAllByUser(User user);

}
