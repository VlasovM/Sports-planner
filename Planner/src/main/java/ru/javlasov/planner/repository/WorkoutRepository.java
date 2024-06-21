package ru.javlasov.planner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.planner.model.User;
import ru.javlasov.planner.model.Workout;

import java.util.List;

public interface WorkoutRepository extends ListCrudRepository<Workout, Long> {

    @NonNull
    @EntityGraph(value = "workout-entity-graph")
    List<Workout> findAllByUser(User user);

}
