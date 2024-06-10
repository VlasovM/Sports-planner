package ru.javlasov.sportsplanner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.sportsplanner.model.Workout;
import ru.javlasov.sportsplanner.model.User;

import java.util.List;

public interface WorkoutRepository extends ListCrudRepository<Workout, Long> {

    @NonNull
    @EntityGraph(value = "workout-entity-graph")
    List<Workout> findAllByUser(User user);

}
