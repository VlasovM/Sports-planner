package ru.javlasov.sportsplanner.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import ru.javlasov.sportsplanner.model.Tournament;
import ru.javlasov.sportsplanner.model.User;

import java.util.List;

public interface TournamentRepository extends ListCrudRepository<Tournament, Long> {

    @NonNull
    @EntityGraph(value = "tournament-entity-graph")
    List<Tournament> findAllByUser(User user);

}
