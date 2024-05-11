package ru.javlasov.sportsplanner.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.javlasov.sportsplanner.model.Tournament;

public interface TournamentRepository extends ListCrudRepository<Tournament, Long> {
}
